package com.Journal.LearningJournal;

import jakarta.persistence.*;

@Entity
@Table(name = "Klassen")
public class Klasse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int klassenID;

    private String klassenname;

    // Getter und Setter
    public int getKlassenID() {
        return klassenID;
    }

    public void setKlassenID(int klassenID) {
        this.klassenID = klassenID;
    }

    public String getKlassenname()
    {
        return klassenname;
    }

    public void setKlassenname(String klassenname) {
        this.klassenname = klassenname;

    }
}