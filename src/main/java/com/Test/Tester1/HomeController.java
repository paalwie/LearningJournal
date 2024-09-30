package com.Test.Tester1;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        // Benutzername des eingeloggten Benutzers hinzufügen
        if (currentUser != null) {
            model.addAttribute("benutzername", currentUser.getUsername());
        } else {
            model.addAttribute("benutzername", "Gast");
        }
        return "adminDashboard";  // adminDashboard.html
    }

    @GetMapping("/start")
    public String startPage(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        // Benutzername des eingeloggten Benutzers hinzufügen
        if (currentUser != null) {
            model.addAttribute("benutzername", currentUser.getUsername());
        } else {
            model.addAttribute("benutzername", "Gast");
        }
        return "start";  // start.html
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // login.html
    }

}
