package com.project.dentistoffice.dto;

public class AppointmentTimeDTO {
    private String date;
    private boolean lastHour;

    public AppointmentTimeDTO() {
    }

    public AppointmentTimeDTO(String date, boolean lastHour) {
        this.date = date;
        this.lastHour = lastHour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isLastHour() {
        return lastHour;
    }

    public void setLastHour(boolean lastHour) {
        this.lastHour = lastHour;
    }
}
