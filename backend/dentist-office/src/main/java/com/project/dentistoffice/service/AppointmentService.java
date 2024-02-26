package com.project.dentistoffice.service;

import com.project.dentistoffice.dto.AppointmentDTO;
import com.project.dentistoffice.dto.AppointmentIdDTO;
import com.project.dentistoffice.dto.AppointmentTimeDTO;
import com.project.dentistoffice.exception.CannotCreateObjectException;
import com.project.dentistoffice.exception.ObjectNotFoundException;
import com.project.dentistoffice.model.Appointment;
import com.project.dentistoffice.model.CancellationPeriod;
import com.project.dentistoffice.model.User;
import com.project.dentistoffice.repository.AppointmentRepository;
import com.project.dentistoffice.repository.PeriodRepository;
import com.project.dentistoffice.repository.RoleRepository;
import com.project.dentistoffice.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PeriodRepository periodRepository;

//    @Autowired
//    private JavaMailSender mailSender;

//    private void sendEmail(String recipientEmail, String content)
//            throws MessagingException, UnsupportedEncodingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setFrom("appe0664@gmail.com", "Dental Office Support");
//        helper.setTo(recipientEmail);
//
//        String subject = "Appointment changes";
//
//        helper.setSubject(subject);
//
//        helper.setText(content, true);
//
//        mailSender.send(message);
//    }

    public Appointment addAppointment(AppointmentDTO dto, String email) {
        List<String> times = generateTimes(new AppointmentTimeDTO(dto.getStartDate(), dto.getDuration() == 60));
        if (!times.contains(dto.getStartTime())) {
            throw new CannotCreateObjectException("Cannot create appointment");
        }
        Appointment appointment = new Appointment();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = dateFormat.parse(dto.getStartDate() + " " + dto.getStartTime());
            if (!isDateInFuture(date)) {
                throw new CannotCreateObjectException("Appointment should be in the future");
            }
            appointment.setDateTime(date);
//            System.out.println("Parsed Date: " + date);
        } catch (ParseException e) {
            throw new CannotCreateObjectException("Error parsing date: " + e.getMessage());
        }

        User patient = userRepository.findUserByEmail(dto.getPatient());
        User eUser = userRepository.findUserByEmail(email);
        if (patient == null) {
            if (!dto.getPatient().equals(email) && eUser.getRole().getName().equals("ROLE_PATIENT")) {
                throw new CannotCreateObjectException("Cannot create appointment");
            }
            patient = new User();
            patient.setRole(roleRepository.findByName("ROLE_PATIENT"));
            patient.setEmail(dto.getPatient());
            patient = userRepository.saveAndFlush(patient);
        } else {
            if (patient.getRole().getName().equals("ROLE_DENTIST")) {
                throw new CannotCreateObjectException("Cannot create appointment");
            }
            if (!eUser.getRole().getName().equals("ROLE_DENTIST") && !eUser.getEmail().equals(patient.getEmail())) {
                throw new CannotCreateObjectException("Cannot create appointment");
            }
        }
        appointment.setUser(patient);
        appointment.setCancelled(false);
        appointment.setLastHour(dto.getDuration() == 60);
        appointment = appointmentRepository.saveAndFlush(appointment);
//        User dentist = userRepository.findByRole(roleRepository.findByName("ROLE_DENTIST"));
//        String content = "Appointment for " + dto.getStartDate() + " " + dto.getStartTime() + "is just made!";
//        this.sendEmail(patient.getEmail(), content);
//        this.sendEmail(dentist.getEmail(), content);
        return appointment;
    }

    private boolean isDateInFuture(Date date) {
        Date currentDate = new Date();
        return date.after(currentDate);
    }

    public List<AppointmentIdDTO> findAll(String email, boolean day) {
        User user = userRepository.findUserByEmail(email);
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date todayStart = calendar.getTime();
        Date end;

        if (day) {
            calendar.add(Calendar.HOUR_OF_DAY, 24);
            end = calendar.getTime();
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            end = calendar.getTime();
        }

        List<Appointment> appointments;
        if (user.getRole().getName().equals("ROLE_PATIENT")) {
            appointments = appointmentRepository.findAllByUserAndIsCancelled(user, false);
        } else {
            appointments = appointmentRepository.findAllByIsCancelled(false);
        }

        List<Appointment> dayAppointments = appointments.stream()
                .filter(obj -> obj.getDateTime().after(todayStart) && obj.getDateTime().before(end))
                .collect(Collectors.toList());
        List<AppointmentIdDTO> appointmentDTOList = new ArrayList<>();
        for (Appointment appointment : dayAppointments) {
            appointmentDTOList.add(new AppointmentIdDTO(appointment));
        }
        return appointmentDTOList;
    }

    public void deleteAppointment(Long id, String email) {
        long cancelletionDeadline = this.getPeriod();
        User user = userRepository.findUserByEmail(email);
        Optional<Appointment> opt = appointmentRepository.findByIdAndIsCancelled(id, false);
        if (opt.isPresent()) {
            Appointment appointment = opt.get();
            Calendar calendar = Calendar.getInstance();
            Date now = new Date();
            calendar.setTime(appointment.getDateTime());
            calendar.add(Calendar.HOUR_OF_DAY, - (int) cancelletionDeadline);
            Date newDate = calendar.getTime();
            if (now.after(newDate) && user.getRole().getName().equals("ROLE_PATIENT")) {
                throw new CannotCreateObjectException("It's too soon to cancel this appointment");
            }
            if (appointment.getUser().getEmail().equals(email) || user.getRole().getName().equals("ROLE_DENTIST")) {
                appointment.setCancelled(true);
                appointmentRepository.saveAndFlush(appointment);
//                String content = "Appointment for " + appointment.getDateTime().toString() + "is just deleted!";
//                this.sendEmail(appointment.getUser().getEmail(), content);
//                User dentist = userRepository.findByRole(roleRepository.findByName("ROLE_DENTIST"));
//                this.sendEmail(dentist.getEmail(), content);
            } else {
                throw new ObjectNotFoundException("Appointment not yours");
            }
        } else {
            throw new ObjectNotFoundException("Appointment not found");
        }
    }

    public void putInitialPeriod() {
        List<CancellationPeriod> cancellationPeriodList = periodRepository.findAll();
        if (cancellationPeriodList.size() == 0 ) {
            CancellationPeriod cancellationPeriod = new CancellationPeriod();
            cancellationPeriod.setPeriod(24);
            cancellationPeriod.setId(1L);
            periodRepository.saveAndFlush(cancellationPeriod);
        } else {
            System.out.println("tu je vec");
        }
    }

    public void changeCancellationPeriod(long period) {
        Optional<CancellationPeriod> cancellationPeriodOpt = periodRepository.findById(1L);
        if (cancellationPeriodOpt.isPresent()) {
            CancellationPeriod cancellationPeriod = cancellationPeriodOpt.get();
            cancellationPeriod.setPeriod(period);
            periodRepository.saveAndFlush(cancellationPeriod);
        }
    }

    public long getPeriod() {
        Optional<CancellationPeriod> cancellationPeriodOpt = periodRepository.findById(1L);
        if (cancellationPeriodOpt.isPresent()) {
            CancellationPeriod cancellationPeriod = cancellationPeriodOpt.get();
            return cancellationPeriod.getPeriod();
        }
        return 24L;
    }

    public List<String> generateTimes(AppointmentTimeDTO dto) {
        String DtoDate = dto.getDate();
        boolean lastsHour = dto.isLastHour();
        List<String> availableAppointments = new ArrayList<>();
        List<String> potentialAppointments = this.getAllTimes(lastsHour);
        Set<String> toBeExcluded = new HashSet<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = dateFormat.parse(DtoDate + " 00:00");
            List<Appointment> scheduledAppointments = getAll(date);
            System.out.println(scheduledAppointments);
            for (String potential : potentialAppointments) {
                Date appointmentStart = dateFormat.parse(DtoDate + " " + potential);
                calendar.setTime(appointmentStart);
                if (dto.isLastHour()) {
                    calendar.add(Calendar.MINUTE, 60);
                } else {
                    calendar.add(Calendar.MINUTE, 30);
                }
                Date appointmentEnd = calendar.getTime();
//                System.out.println(appointmentStart + "              "  +appointmentEnd);
                for (Appointment scheduled : scheduledAppointments) {
                    Date scheduledDateStart = scheduled.getDateTime();
                    calendar.setTime(scheduledDateStart);
                    scheduledDateStart = calendar.getTime();
                    if (scheduled.isLastHour()) {
                        calendar.add(Calendar.MINUTE, 60);
                    } else {
                        calendar.add(Calendar.MINUTE, 30);
                    }
                    Date scheduledDateEnd = calendar.getTime();
                    if ((scheduledDateStart.after(appointmentStart) && scheduledDateStart.before(appointmentEnd)) ||
                            (scheduledDateStart.equals(appointmentStart) && scheduledDateStart.before(appointmentEnd)) ||
                            (scheduledDateEnd.after(appointmentStart) && scheduledDateEnd.before(appointmentEnd)) ||
                            (scheduledDateEnd.after(appointmentStart) && scheduledDateEnd.equals(appointmentEnd))) {
                        System.out.println(appointmentStart + "        "  +appointmentEnd);
                        toBeExcluded.add(potential);
                    }
//                    if (scheduledDateStart.after(appointmentStart)) {
//                        System.out.println("after " + scheduledDateStart + "        "  +appointmentStart);
//                    }
//                    if (scheduledDateStart.before(appointmentEnd)) {
//                        System.out.println("before " + scheduledDateStart + "        "  +appointmentEnd);
//                    }
//
//                    if (scheduledDateStart.equals(appointmentStart)) {
//                        System.out.println("start " + scheduledDateStart + "        "  +appointmentStart);
//                    }
//                    if (scheduledDateStart.equals(appointmentEnd)) {
//                        System.out.println("end " + scheduledDateStart + "        "  +appointmentEnd);
//                    }
                }
            }
        } catch (ParseException e) {
            throw new CannotCreateObjectException("Error parsing date: " + e.getMessage());
        }
        for (String potential : potentialAppointments) {
            if (!toBeExcluded.contains(potential)) {
                availableAppointments.add(potential);
            }
        }
        System.out.println(availableAppointments);
        return availableAppointments;
    }

    private List<String> getAllTimes(boolean lastsHours) {
        List<String> times = new ArrayList<>();
        for (int i = 9; i < 17; i++) {
            if (i == 9) {
                times.add("0" + Integer.toString(i) + ":00");
                times.add("0" + Integer.toString(i) + ":30");
            } else {
                times.add(Integer.toString(i) + ":00");
                if (i != 16 || !lastsHours) {
                    times.add(Integer.toString(i) + ":30");
                }
            }
        }
        return times;
    }

    private List<Appointment> getAll(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        Date end = calendar.getTime();
        List<Appointment> appointments = appointmentRepository.findAll();
        List<Appointment> ret = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDateTime().after(date) && appointment.getDateTime().before(end) && !appointment.isCancelled()) {
                ret.add(appointment);
            }
        }
        return ret;
    }


}
