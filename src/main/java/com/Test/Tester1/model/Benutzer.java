package com.Test.Tester1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "benutzer")
public class Benutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long benutzerid;

    @Column(nullable = false, unique = true)
    private String benutzername;

    @Column(nullable = false)
    private String passwort;

    @Column(nullable = false)
    private int rolleid;

    // Weitere Felder, falls benötigt

    // Getter und Setter
    public Long getBenutzerid() {
        return benutzerid;
    }

    public void setBenutzerid(Long benutzerid) {
        this.benutzerid = benutzerid;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public int getRolleid() {
        return rolleid;
    }

    public void setRolleid(int rolleid) {
        this.rolleid = rolleid;
    }
}
