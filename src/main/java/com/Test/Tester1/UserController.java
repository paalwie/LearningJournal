package com.Test.Tester1;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.model.Klassen;
import com.Test.Tester1.model.Vortragsthema;
import com.Test.Tester1.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository benutzerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository klassenRepository;


    public UserController(UserRepository benutzerRepository, PasswordEncoder passwordEncoder, UserRepository klassenRepository) {
        this.benutzerRepository = benutzerRepository;
        this.passwordEncoder = passwordEncoder;
        this.klassenRepository = klassenRepository;
    }

    // Zeigt die Benutzerstartseite an
    @GetMapping("/home")
    public String userHome(Model model) {
        // Aktuelle Authentifizierung abrufen
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Benutzerinformationen aus der Datenbank abrufen
        Benutzer benutzer = benutzerRepository.findByBenutzername(username);

// Hole das Vortragsthema (wenn vorhanden)
        Vortragsthema vortragsthema = null;
        if (benutzer != null && benutzer.getVortragsthema() != null) {
            vortragsthema = benutzer.getVortragsthema();
        }

        LocalDate dueDate = calculateDueDate();

        model.addAttribute("vortragsthema", vortragsthema != null ? vortragsthema.getThema() : null);
        model.addAttribute("dueDate", dueDate);

        // Den Benutzernamen in das Model hinzufügen, um ihn in Thymeleaf anzuzeigen
        model.addAttribute("benutzername", benutzer.getBenutzername());
        model.addAttribute("klassenname", benutzer.getKlassen().getKlassenname());

        return "userHome";  // userHome.html anzeigen
    }

    private LocalDate calculateDueDate() {
        LocalDate now = LocalDate.now();
        LocalDate lastThursday = now.with(TemporalAdjusters.lastInMonth(java.time.DayOfWeek.THURSDAY));

        // Überprüfen, ob das heutige Datum nach dem letzten Donnerstag liegt
        if (now.isAfter(lastThursday)) {
            lastThursday = lastThursday.plusMonths(1); // Nächster Monat
        }

        return lastThursday;
    }

    private String getVortragsthemaForUser(Long userId) {
        // Logik zur Abfrage des Vortragsthemas des Benutzers
        // Gibt ein Thema oder null zurück, wenn kein Thema vergeben ist
        // Beispiel-Rückgabe
        return null; // oder der tatsächliche Thema-String
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
        model.addAttribute("klassenname", benutzer.getKlassen().getKlassenname());
        return "change-password";  // change-password.html anzeigen
    }


    // Verarbeitet die Passwortänderung
    @PostMapping("/change-password")
    public String changePassword(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 Model model) {
        // Hole den aktuell authentifizierten Benutzer aus der Datenbank

        Benutzer benutzer = benutzerRepository.findByBenutzername(userDetails.getUsername());// Überprüfe, ob das aktuelle Passwort korrekt ist
        if (!passwordEncoder.matches(currentPassword, benutzer.getPasswort())) {
            model.addAttribute("error", "Das aktuelle Passwort ist falsch.");
        } else {
            // Setze das neue Passwort
            benutzer.setPasswort(passwordEncoder.encode(newPassword));
            benutzerRepository.save(benutzer);
            model.addAttribute("success", "Passwort wurde erfolgreich geändert.");
        }


        // Den Benutzernamen in das Model wieder hinzufügen
        model.addAttribute("benutzername", benutzer.getBenutzername());

        return "change-password";  // Zeige die Seite erneut an
    }
}
