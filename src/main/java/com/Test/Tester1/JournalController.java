package com.Test.Tester1;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.model.Journal;
import com.Test.Tester1.repository.JournalRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class JournalController {

    private final JournalRepository journalRepository;

    public JournalController(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    @GetMapping("/user/journal")
    public String getUserJournal(Model model, Authentication authentication) {
        // Nehmen wir an, der Benutzername entspricht der Benutzer-ID
        String username = authentication.getName();

        Benutzer user = (Benutzer) authentication.getPrincipal(); // Casten zu Ihrer Benutzer-Entität
        Long userId = user.getBenutzerid();

        List<Journal> userEntries = journalRepository.findByBenutzerid(userId);

        model.addAttribute("entries", userEntries);

        return "userJournal";  // Die HTML-Vorlage, in der die Tabelle angezeigt wird
    }
}
