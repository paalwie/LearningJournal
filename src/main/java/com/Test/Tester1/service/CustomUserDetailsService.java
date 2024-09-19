package com.Test.Tester1.service;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.repository.BenutzerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final BenutzerRepository benutzerRepository;

    public CustomUserDetailsService(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String benutzername) throws UsernameNotFoundException {
        Benutzer benutzer = benutzerRepository.findByBenutzername(benutzername)
                .orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden: " + benutzername));

        // Erstelle ein User-Objekt, das Spring Security versteht
        return User.withUsername(benutzer.getBenutzername())
                .password(benutzer.getPasswort()) // Stelle sicher, dass das Passwort verschlüsselt ist!
                .roles("USER") // Rollen können ebenfalls aus der DB geladen werden, wenn du sie speicherst
                .build();
    }
}
