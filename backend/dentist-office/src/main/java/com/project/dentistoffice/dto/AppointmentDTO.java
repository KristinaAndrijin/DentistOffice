package com.project.dentistoffice.dto;

public class AppointmentDTO {
    private String startDate;
    private String startTime;
    private String patient;
    private int duration;

    public AppointmentDTO() {
    }

    public AppointmentDTO(String startDate, String startTime, String patient, int duration) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.patient = patient;
        this.duration = duration;
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
