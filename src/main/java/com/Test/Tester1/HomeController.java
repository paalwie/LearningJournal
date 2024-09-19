package com.Test.Tester1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    // Weiterleitung von "/" auf "/start"
    @GetMapping("/")
    public RedirectView redirectToStartPage() {
        return new RedirectView("/start");
    }

    // Öffentliche Startseite
    @GetMapping("/start")
    public String startPage() {
        return "start";  // Gibt die start.html-Seite zurück
    }

    // Benutzerdefinierte Login-Seite
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Gibt die login.html-Seite zurück
    }

    // Geschützte Hauptseite nach erfolgreicher Anmeldung
    @GetMapping("/home")
    public String homePage() {
        return "home";  // Geschützte Seite für angemeldete Benutzer
    }
}
