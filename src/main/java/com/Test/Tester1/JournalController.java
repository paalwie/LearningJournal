package com.Test.Tester1;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.model.Journal;
import com.Test.Tester1.repository.JournalRepository;
import com.Test.Tester1.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class JournalController {

    private final JournalRepository journalRepository;
    private final UserRepository userRepository;

    public JournalController(JournalRepository journalRepository, UserRepository userRepository) {
        this.journalRepository = journalRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/journal")
    public String getUserJournal(Model model, Authentication authentication) {
        // Hole den Benutzernamen aus dem Authentication-Objekt
        String username = authentication.getName();

        // Finde den Benutzer anhand des Benutzernamens (du kannst hier deinen Benutzer-Repository verwenden)
        Benutzer user = userRepository.findByBenutzername(username); // Implementiere diese Methode in deinem Benutzer-Repository

        if (user == null) {
            throw new UsernameNotFoundException("Benutzer nicht gefunden: " + username);
        }

        Long userId = user.getBenutzerid();

        List<Journal> userEntries = journalRepository.findByBenutzerid(userId);

        model.addAttribute("entries", userEntries);

        return "userJournal";
    }
}
