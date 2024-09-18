package com.Journal.LearningJournal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    // Injizieren des PasswordEncoder
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        System.out.println("Das ist falsch!");
        return "login"; // Gibt die login.html zurück
    }

    /*

    Laut Chatgpt überflüssig

    @PostMapping(value = "/login")
    public String processLogin(@ModelAttribute LoginRequest loginRequest) {

        System.out.println("Form wurde abgesendet mit: Benutzername=" + loginRequest.getBenutzername() + ", Passwort=" + loginRequest.getPasswort());

        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getBenutzername());

        // Benutzer finden und Passwort überprüfen
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden"));
        if (!passwordEncoder.matches(loginRequest.getPasswort(), user.getPasswort())) {
            return "login"; // Ungültiges Passwort
        }

        // Rollenbasierte Weiterleitung
        int roleId = user.getRolle().getRolleID();
        switch (roleId) {
            case 1:
                return "admin";
            case 2:
                return "user";
            default:
                return "error";
        }
    } */
}

@Setter
@Getter
class LoginRequest {
    private String benutzername;
    private String passwort;
    // Getter und Setter

}
