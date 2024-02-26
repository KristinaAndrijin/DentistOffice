package com.project.dentistoffice.service;

import com.project.dentistoffice.dto.AppointmentDTO;
import com.project.dentistoffice.dto.AppointmentIdDTO;
import com.project.dentistoffice.exception.CannotCreateObjectException;
import com.project.dentistoffice.exception.ObjectNotFoundException;
import com.project.dentistoffice.model.Appointment;
import com.project.dentistoffice.model.CancellationPeriod;
import com.project.dentistoffice.model.User;
import com.project.dentistoffice.repository.AppointmentRepository;
import com.project.dentistoffice.repository.PeriodRepository;
import com.project.dentistoffice.repository.RoleRepository;
import com.project.dentistoffice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private List<String> getAllTimes() {
        List<String> times = new ArrayList<>();
        for (int i = 9; i < 17; i++) {
            times.add(Integer.toString(i) + ":00");
            times.add(Integer.toString(i) + ":30");
        }
        return times;
    }

    public Appointment addAppointment(AppointmentDTO dto, String email) {
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
            } else {
                if (!dto.getPatient().equals(email)) {
                    throw new CannotCreateObjectException("Cannot create appointment");
                }
            }
        }
        appointment.setUser(patient);
        appointment.setCancelled(false);
        appointment.setLastHour(dto.getDuration() == 60);
        return appointmentRepository.saveAndFlush(appointment);
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
        User user = userRepository.findUserByEmail(email);
        Optional<Appointment> opt = appointmentRepository.findByIdAndIsCancelled(id, false);
        if (opt.isPresent()) {
            Appointment appointment = opt.get();
            if (appointment.getUser().getEmail().equals(email) || user.getRole().getName().equals("ROLE_DENTIST")) {
                appointment.setCancelled(true);
                appointmentRepository.saveAndFlush(appointment);
            } else {
                throw new ObjectNotFoundException("Appointment not yours");
            }
        } else {
            throw new ObjectNotFoundException("Appointment not found");
        }
    }

    public void putInitialPeriod() {
        CancellationPeriod cancellationPeriod = new CancellationPeriod();
        cancellationPeriod.setPeriod(24);
        cancellationPeriod.setId(1L);
        periodRepository.saveAndFlush(cancellationPeriod);
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
}
