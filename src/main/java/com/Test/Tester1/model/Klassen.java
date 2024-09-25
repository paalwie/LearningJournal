package com.Test.Tester1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "klassen")
public class Klassen {

    @Id
    private Long klassenid;

    @Column(nullable = false)
    private String klassenname;

    public Long getKlassenid() {
        return klassenid;
    }
    public void setKlassenid(Long klassenid) {
        this.klassenid = klassenid;
    }
    public String getKlassenname() {
        return klassenname;
    }
    public void setKlassenname(String klassenname) {
        this.klassenname = klassenname;
    }

}
