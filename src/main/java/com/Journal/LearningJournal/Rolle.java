package com.Journal.LearningJournal;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Rolle")
public class Rolle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rolleID;

    private String rollenname;

    @OneToMany(mappedBy = "rolle")
    private List<User> benutzer;

    // Getter und Setter
    public int getRolleID() {
        return rolleID;
    }

    public void setRolleID(int rolleID) {
        this.rolleID = rolleID;
    }

    public String getRollenname() {
        return rollenname;
    }

    public void setRollenname(String rollenname) {
        this.rollenname = rollenname;
    }

    public List<User> getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(List<User> benutzer) {
        this.benutzer = benutzer;
    }
}