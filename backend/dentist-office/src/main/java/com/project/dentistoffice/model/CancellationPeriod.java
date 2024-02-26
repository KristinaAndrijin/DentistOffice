package com.project.dentistoffice.model;

import jakarta.persistence.*;

@Entity
@Table(name="period")
public class CancellationPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    private long period;

    public CancellationPeriod() {
    }

    public CancellationPeriod(Long id, long period) {
        this.id = id;
        this.period = period;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}
