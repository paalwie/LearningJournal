package com.Test.Tester1.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "journal")
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eintragid;

    @Column(nullable = false)
    private String titel;

    @Column(nullable = false)
    private String inhalt;

    @Column(nullable = false)
    private Long benutzerid;

    @Column(nullable = false)
    private LocalDate erstellungsdatum;

    @Column(nullable = false)
    private LocalTime erstellungszeit;

    // Getter und Setter
    public Long getEintragid() {
        return eintragid;
    }

    public void setEintragid(Long eintragid) {
        this.eintragid = eintragid;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getInhalt() {
        return inhalt;
    }

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

    public Long getBenutzerid() {
        return benutzerid;
    }

    public void setBenutzerid(Long benutzerid) {
        this.benutzerid = benutzerid;
    }

    public LocalDate getErstellungsdatum() {
        return erstellungsdatum;
    }

    public void setErstellungsdatum(LocalDate erstellungsdatum) {
        this.erstellungsdatum = erstellungsdatum;
    }

   public LocalTime getErstellungszeit() {
       return erstellungszeit;
    }

    public void setErstellungszeit(LocalTime erstellungszeit) {
        this.erstellungszeit = erstellungszeit;
    }
    public void setuhrzeit_mit_millisekunden(LocalTime now) {

    }

    public void getuhrzeit_ohne_millisekunden(LocalTime now) {
    }
}

