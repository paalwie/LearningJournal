package com.Test.Tester1;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.model.Journal;
import com.Test.Tester1.model.Themen;
import com.Test.Tester1.repository.CategoryRepository;
import com.Test.Tester1.repository.JournalRepository;
import com.Test.Tester1.repository.UserRepository;
import com.Test.Tester1.service.KategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
public class JournalController {

    private final JournalRepository journalRepository;
    private final UserRepository userRepository;

    @Autowired
    private KategorieService kategorieService;

    @Autowired
    private CategoryRepository categoryRepository;

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
        Optional<Journal> journalEntryOptional = journalRepository.findById(id);

        if (journalEntryOptional.isPresent()) {

            Journal journalEntry = journalEntryOptional.get();

            // Kategorie-Objekt direkt aus dem Journal-Eintrag holen
            Themen kategorie = journalEntry.getKategorie();  // Holt das Themen-Objekt (die Kategorie)


            if (kategorie != null) {
                Long kategorieId = kategorie.getKategorieid();  // Holt die Kategorie-ID aus dem Themen-Objekt

                // Füge den Journal-Eintrag und die Kategorie dem Model hinzu
                model.addAttribute("entry", journalEntry);
                model.addAttribute("kategorie", kategorie);
            }

            return "journalentry";  // HTML-Seite, auf der der Eintrag angezeigt wird
        } else {
            return "redirect:/user/journal";  // Wenn der Eintrag nicht existiert, zurück zur Liste
        }
    }

    // GET-Methode zum Anzeigen der Seite "journalcreate.html"
    @GetMapping("/user/journal/create")
    public String showCreateJournalForm(Model model) {
        List<Themen> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("journal", new Journal());
        return "journalcreate";
    }

    // POST-Methode zum Erstellen eines neuen Journaleintrags
    @PostMapping("/user/journal/create")
    public String createJournalEntry(@ModelAttribute Journal journal, Model model, @RequestParam Long kategorieid, @RequestParam String titel, @RequestParam String inhalt, Authentication authentication) {

        String username = authentication.getName();
        Benutzer user = userRepository.findByBenutzername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Benutzer nicht gefunden: " + username);
        }

        Themen kategorie = kategorieService.findThemenById(kategorieid);
        journal.setKategorie(kategorie);

        //Journal newJournal = new Journal();
        journal.setTitel(titel);
        journal.setInhalt(inhalt);
        journal.setBenutzerid(user.getBenutzerid()); // Setzt die Benutzer-ID des aktuellen Nutzers
        journal.setErstellungsdatum(LocalDate.now()); // Automatisches Datum
        journal.setErstellungszeit(LocalTime.now());  // Automatische Uhrzeit

        // Speichern des neuen Eintrags
        journalRepository.save(journal);

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


                // Füge alle Kategorien dem Model hinzu
                List<Themen> categories = categoryRepository.findAll();
                model.addAttribute("categories", categories);

                return "journaledit"; // Journal bearbeiten Seite
            }
        }
        return "redirect:/user/journal"; // Falls kein Zugriff, zurück zur Übersicht
    }

    @PostMapping("/user/journal/update/{eintragid}")
    public String updateJournalEntry(@PathVariable Long eintragid, @RequestParam String titel, @RequestParam String inhalt, @RequestParam Long kategorie, Authentication authentication) {
        Optional<Journal> journalEntry = journalRepository.findById(eintragid);
        if (journalEntry.isPresent()) {
            Journal entry = journalEntry.get();
            Benutzer user = userRepository.findByBenutzername(authentication.getName());
            if (entry.getBenutzerid().equals(user.getBenutzerid())) {
                entry.setTitel(titel);
                entry.setInhalt(inhalt);

                Themen selectedKategorie = kategorieService.findThemenById(kategorie);
                entry.setKategorie(selectedKategorie); // Setze die Kategorie im Journal

                journalRepository.save(entry);
            }
        }
        return "redirect:/user/journal"; // Nach dem Update zurück zur Übersicht
    }


}
