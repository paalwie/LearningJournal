package com.Test.Tester1;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminSettingsController {

    private final UserRepository benutzerRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSettingsController(UserRepository benutzerRepository, PasswordEncoder passwordEncoder) {
        this.benutzerRepository = benutzerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Zeigt die Passwortänderungsseite an
    @GetMapping("/admin-Change-Password")
    public String showChangePasswordPage(Model model) {
        // Hole den aktuell authentifizierten Benutzer
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        // Benutzerinformationen aus der Datenbank abrufen
        Benutzer benutzer = benutzerRepository.findByBenutzername(username);
        // Füge den Benutzernamen zum Model hinzu, um ihn in Thymeleaf anzuzeigen
        model.addAttribute("benutzername", benutzer.getBenutzername());
        model.addAttribute("klassenname", benutzer.getKlassen().getKlassenname());
        return "admin-Change-Password";  // change-password.html anzeigen
    }
    @PostMapping("/admin-Change-Password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Benutzer benutzer = benutzerRepository.findByBenutzername(username);

        // Überprüfen, ob das aktuelle Passwort korrekt ist
        if (!passwordEncoder.matches(currentPassword, benutzer.getPasswort())) {
            model.addAttribute("error", "Das aktuelle Passwort ist falsch.");
            return "admin-Change-Password"; // Zurück zur Passwortänderungsseite
        }

        // Neues Passwort setzen und speichern
        benutzer.setPasswort(passwordEncoder.encode(newPassword));
        benutzerRepository.save(benutzer);

        model.addAttribute("success", "Passwort erfolgreich geändert.");
        return "redirect:/admin/dashboard"; // Hier die gewünschte Weiterleitungsseite angeben
    }

}
