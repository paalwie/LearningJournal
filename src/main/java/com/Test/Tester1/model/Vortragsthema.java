package com.Test.Tester1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vortragsthemen")
public class Vortragsthema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vortragsthemaID;

    @ManyToOne
    @JoinColumn(name = "kategorieID", nullable = false)
    private Themen themen;


    private String thema;

    // Getter und Setter
    public Long getVortragsthemaID() {
        return vortragsthemaID;
    }

    public void setVortragsthemaID(Long vortragsthemaID) {
        this.vortragsthemaID = vortragsthemaID;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public Themen getThemen() {
        return themen;
    }

    public void setThemen(Themen themen) {
        this.themen = themen;
    }

}
