package com.project.dentistoffice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="appointments")
@Entity
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
//    private byte startHour; // 9-16
//    private boolean startHourly; // pocinje u pun sat
    private boolean lastHour; // traje sat vremena
    private Date dateTime;
    private boolean isCancelled;

    public Appointment() {
    }

    public Appointment(Long id, User user, /*byte startHour, boolean startHourly, */ boolean lastHour, Date dateTime, boolean isCancelled) {
        this.id = id;
        this.user = user;
//        this.startHour = startHour;
//        this.startHourly = startHourly;
        this.lastHour = lastHour;
        this.dateTime = dateTime;
        this.isCancelled = isCancelled;
    }

    public Appointment(User user, /*byte startHour, boolean startHourly, */ boolean lastHour, Date dateTime, boolean isCancelled) {
        this.user = user;
//        this.startHour = startHour;
//        this.startHourly = startHourly;
        this.lastHour = lastHour;
        this.dateTime = dateTime;
        this.isCancelled = isCancelled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public byte getStartHour() {
//        return startHour;
//    }
//
//    public void setStartHour(byte startHour) {
//        this.startHour = startHour;
//    }
//
//    public boolean isStartHourly() {
//        return startHourly;
//    }
//
//    public void setStartHourly(boolean startHourly) {
//        this.startHourly = startHourly;
//    }
//
    public boolean isLastHour() {
        return lastHour;
    }

    public void setLastHour(boolean lastHour) {
        this.lastHour = lastHour;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", user=" + user.getEmail() +
                ", lastHour=" + lastHour +
                ", dateTime=" + dateTime +
                ", isCancelled=" + isCancelled +
                '}';
    }
}
