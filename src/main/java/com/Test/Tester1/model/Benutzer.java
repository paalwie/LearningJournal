package com.Test.Tester1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "benutzer")
public class Benutzer {

    @Id
    private Long benutzerid;

    @Column(nullable = false, unique = true)
    private String benutzername;

    @Column(nullable = false)
    private String passwort;

    @Column(nullable = false)
    private int rolleid;


    @OneToMany(mappedBy = "benutzerid", cascade = CascadeType.REMOVE)
    private List<Journal> journals;

    // Beziehung zur Klasse 'Klassen' (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "klassenid", referencedColumnName = "klassenid")
    private Klassen klassen;

    @ManyToOne
    @JoinColumn(name = "vortragsthemaid")
    private Vortragsthema vortragsthema; // Beziehung zur Vortragsthemen-Tabelle

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

    public Klassen getKlassen() {
        return klassen;
    }

    public void setKlassen(Klassen klassen) {
        this.klassen = klassen;
    }

    public Object getKlassenid() {
        return klassen;
    }

    public void setKlassenid(Object klassenid) {
    }

    public Vortragsthema getVortragsthema() {
        return vortragsthema;
    }

    public void setVortragsthema(Vortragsthema vortragsthema) {
        this.vortragsthema = vortragsthema;
    }

}
