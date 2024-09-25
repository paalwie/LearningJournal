package com.Test.Tester1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "kategorie")
public class Themen {

    @Id
    private Long kategorieid;

    @Column(nullable = false, unique = true)
    private String kategoriename;

    @Column(nullable = false)
    private String kategoriebeschreibung;

    public Long getKategorieid() {
        return kategorieid;
    }

    public void setKategorieid(Long kategorieid) {
        this.kategorieid = kategorieid;
    }

    public String getKategoriename() {
        return kategoriename;
    }

    public void setKategoriename(String kategoriename) {
        this.kategoriename = kategoriename;
    }

    public String getKategoriebeschreibung() {
        return kategoriebeschreibung;
    }

    public void setKategoriebeschreibung(String kategoriebeschreibung) {
        this.kategoriebeschreibung = kategoriebeschreibung;
    }
}
