package com.Journal.LearningJournal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {

    // Parameterloser Konstruktor, wird von JPA benötigt
    public Users() {
        // Keine Initialisierungen notwendig
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Verwende IDENTITY, wenn deine DB auto_increment unterstützt
    private Long id;

    private String email;
    private String password;
    private String role;
    private String fullname;

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    // Konstruktor mit Parametern, um einen neuen Benutzer zu erstellen
    public Users(String email, String fullname, String password, String role) {
        this.fullname = fullname;
        this.role = role;
        this.password = password;
        this.email = email;
    }
}
