package com.Test.Tester1;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.model.Journal;
import com.Test.Tester1.repository.JournalRepository;
import com.Test.Tester1.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
public class JournalController {

    private final JournalRepository journalRepository;
    private final UserRepository userRepository;

    public JournalController(JournalRepository journalRepository, UserRepository userRepository) {
        this.journalRepository = journalRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/journal")
    public String getUserJournal(Model model, @RequestParam(defaultValue = "0") int page,  // Seite 0 als Standard
                                 @RequestParam(defaultValue = "15") int size, // 15 Einträge pro Seite
                                 Authentication authentication) {

        // Hole den Benutzernamen aus dem Authentication-Objekt
        String username = authentication.getName();

        // Finde den Benutzer anhand des Benutzernamens (du kannst hier deinen Benutzer-Repository verwenden)
        Benutzer user = userRepository.findByBenutzername(username); // Implementiere diese Methode in deinem Benutzer-Repository

        if (user == null) {
            throw new UsernameNotFoundException("Benutzer nicht gefunden: " + username);
        }

        Long userId = user.getBenutzerid();

        // Paging anfordern
        Pageable pageable = PageRequest.of(page, size);
        Page<Journal> userEntries = journalRepository.findByBenutzerid(userId, pageable);

        model.addAttribute("entries", userEntries);


        int currentPage = page;
        int totalPages = userEntries.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("entries", userEntries);

        return "userJournal";
    }

    // Neue Methode für die detaillierte Ansicht eines Journaleintrags
    @GetMapping("/user/journal/{id}")
    public String getJournalEntry(@PathVariable Long id, Model model) {
        Optional<Journal> journalEntry = journalRepository.findById(id);

        if (journalEntry.isPresent()) {
            model.addAttribute("entry", journalEntry.get());
            return "journalentry";  // HTML-Seite, auf der der Eintrag angezeigt wird
        } else {
            return "redirect:/user/journal";  // Wenn der Eintrag nicht existiert, zurück zur Liste
        }
    }

    // GET-Methode zum Anzeigen der Seite "journalcreate.html"
    @GetMapping("/user/journal/create")
    public String showCreateJournalForm() {
        return "journalcreate";
    }

    // POST-Methode zum Erstellen eines neuen Journaleintrags
    @PostMapping("/user/journal/create")
    public String createJournalEntry(@RequestParam String titel, @RequestParam String inhalt, Authentication authentication) {
        String username = authentication.getName();
        Benutzer user = userRepository.findByBenutzername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Benutzer nicht gefunden: " + username);
        }

        Journal newJournal = new Journal();
        newJournal.setTitel(titel);
        newJournal.setInhalt(inhalt);
        newJournal.setBenutzerid(user.getBenutzerid()); // Setzt die Benutzer-ID des aktuellen Nutzers
        newJournal.setErstellungsdatum(LocalDate.now()); // Automatisches Datum
        newJournal.setErstellungszeit(LocalTime.now());  // Automatische Uhrzeit

        // Speichern des neuen Eintrags
        journalRepository.save(newJournal);

        // Nach der Erstellung zurück zur Liste der Journaleinträge
        return "redirect:/user/journal";
    }

    @PostMapping("/user/journal/delete/{eintragid}")
    public String deleteJournalEntry(@PathVariable Long eintragid, Authentication authentication) {
        Optional<Journal> journalEntry = journalRepository.findById(eintragid);
        if (journalEntry.isPresent()) {
            Journal entry = journalEntry.get();
            // Überprüfen, ob der eingeloggte Benutzer der Besitzer des Eintrags ist
            Benutzer user = userRepository.findByBenutzername(authentication.getName());
            if (entry.getBenutzerid().equals(user.getBenutzerid())) {
                journalRepository.delete(entry);
            }
        }
        return "redirect:/user/journal"; // Zurück zur Journalübersicht
    }

    @GetMapping("/user/journal/edit/{eintragid}")
    public String editJournalEntry(@PathVariable Long eintragid, Model model, Authentication authentication) {
        Optional<Journal> journalEntry = journalRepository.findById(eintragid);
        if (journalEntry.isPresent()) {
            Journal entry = journalEntry.get();
            Benutzer user = userRepository.findByBenutzername(authentication.getName());
            if (entry.getBenutzerid().equals(user.getBenutzerid())) {
                model.addAttribute("journal", entry);
                return "journaledit"; // Journal bearbeiten Seite
            }
        }
        return "redirect:/user/journal"; // Falls kein Zugriff, zurück zur Übersicht
    }

    @PostMapping("/user/journal/update/{eintragid}")
    public String updateJournalEntry(@PathVariable Long eintragid, @RequestParam String titel, @RequestParam String inhalt, Authentication authentication) {
        Optional<Journal> journalEntry = journalRepository.findById(eintragid);
        if (journalEntry.isPresent()) {
            Journal entry = journalEntry.get();
            Benutzer user = userRepository.findByBenutzername(authentication.getName());
            if (entry.getBenutzerid().equals(user.getBenutzerid())) {
                entry.setTitel(titel);
                entry.setInhalt(inhalt);
                journalRepository.save(entry);
            }
        }
        return "redirect:/user/journal"; // Nach dem Update zurück zur Übersicht
    }


}
