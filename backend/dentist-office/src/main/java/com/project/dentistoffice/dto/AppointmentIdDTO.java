package com.project.dentistoffice.dto;

import com.project.dentistoffice.model.Appointment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentIdDTO {
    private long id;
    private String startDate;
    private String startTime;
    private String patient;
    private int duration;

    public AppointmentIdDTO() {
    }

    public AppointmentIdDTO(int id, String startDate, String startTime, String patient, int duration) {
        this.id = id;
        this.startDate = startDate;
        this.startTime = startTime;
        this.patient = patient;
        this.duration = duration;
    }

    public AppointmentIdDTO(Appointment appointment) {
        AppointmentIdDTO dto = new AppointmentIdDTO();
        String pattern = "dd.MM.yyyy.";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String formattedDate = dateFormat.format(appointment.getDateTime());
        this.startDate = formattedDate;

        pattern = "HH:mm";
        dateFormat = new SimpleDateFormat(pattern);
        formattedDate = dateFormat.format(appointment.getDateTime());
        this.startTime = formattedDate;

        this.id = appointment.getId();

        this.duration = appointment.isLastHour() ? 60 : 30;

        this.patient = appointment.getUser().getEmail();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
