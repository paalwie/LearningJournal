package com.Test.Tester1.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "journal")
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eintragid; // Primärschlüssel

    private Long benutzerid; // ID des Benutzers, der den Eintrag erstellt hat

    private String titel; // Titel des Journaleintrags

    private String inhalt; // Inhalt des Journaleintrags

    private LocalDate erstellungsdatum; // Erstellungsdatum

    private LocalTime erstellungszeit; // Erstellungszeit

    // Getter und Setter

    public Long getEintragid() {
        return eintragid;
    }

    public void setEintragid(Long eintragid) {
        this.eintragid = eintragid;
    }

    public Long getBenutzerid() {
        return benutzerid;
    }

    public void setBenutzerid(Long benutzerid) {
        this.benutzerid = benutzerid;
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
}

