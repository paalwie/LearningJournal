package com.Test.Tester1;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserRepository benutzerRepository;

    public UserController(UserRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @GetMapping("/user/home")
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
}
