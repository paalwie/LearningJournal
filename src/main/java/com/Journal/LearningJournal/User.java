package com.Journal.LearningJournal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "benutzer")
public class User {
    // Getter und Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int benutzerID;

    @Column(name = "benutzername")
    private String username;
    private String passwort;

    @ManyToOne
    @JoinColumn(name = "rolleID")
    private Rolle rolle;

    // Getter und Setter für klasse
    @ManyToOne
    @JoinColumn(name = "klassenID", nullable = false) // can be null depending on your logic
    private Klasse klasse;

    public void setKlassen(Klasse invalidClassId) {
    }

    public User orElseThrow(Object userNotFound) {
        return null;
    }
}