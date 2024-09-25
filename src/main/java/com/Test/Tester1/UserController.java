package com.Test.Tester1;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository benutzerRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository benutzerRepository, PasswordEncoder passwordEncoder) {
        this.benutzerRepository = benutzerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Zeigt die Benutzerstartseite an
    @GetMapping("/home")
    public String userHome(Model model) {
        // Aktuelle Authentifizierung abrufen
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Benutzerinformationen aus der Datenbank abrufen
        Benutzer benutzer = benutzerRepository.findByBenutzername(username);

        // Den Benutzernamen in das Model hinzufügen, um ihn in Thymeleaf anzuzeigen
        model.addAttribute("benutzername", benutzer.getBenutzername());

        return "userHome";  // userHome.html anzeigen
    }

    // Zeigt die Passwortänderungsseite an
    @GetMapping("/change-password")
    public String showChangePasswordPage(Model model) {
        // Hole den aktuell authentifizierten Benutzer
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Benutzerinformationen aus der Datenbank abrufen
        Benutzer benutzer = benutzerRepository.findByBenutzername(username);

        // Füge den Benutzernamen zum Model hinzu, um ihn in Thymeleaf anzuzeigen
        model.addAttribute("benutzername", benutzer.getBenutzername());

        return "change-password";  // change-password.html anzeigen
    }
    private String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    // Verarbeitet die Passwortänderung
    @PostMapping("/change-password")
    public String changePassword(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 Model model) {
        // Hole den aktuell authentifizierten Benutzer aus der Datenbank

        Benutzer benutzer = benutzerRepository.findByBenutzername(userDetails.getUsername());

        // Überprüfe, ob das aktuelle Passwort korrekt ist
        if (!passwordEncoder.matches(currentPassword, benutzer.getPasswort())) {
            model.addAttribute("error", "Das aktuelle Passwort ist falsch.");
        } else {
            // Setze das neue Passwort
            benutzer.setPasswort(passwordEncoder.encode(newPassword));
            benutzerRepository.save(benutzer);
            model.addAttribute("success", "Passwort wurde erfolgreich geändert.");
        }

        capitalize(String.valueOf(benutzer));

        // Den Benutzernamen in das Model wieder hinzufügen
        model.addAttribute("benutzername", benutzer.getBenutzername());

        return "change-password";  // Zeige die Seite erneut an
    }
}
