package com.project.dentistoffice.model;

import jakarta.persistence.*;

@Entity
@Table(name="code")
public class DentistCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private int code;

    private boolean used;

    public DentistCode() {
    }

    public DentistCode(Long id, int code, boolean used) {
        this.id = id;
        this.code = code;
        this.used = used;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void isUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }
}
