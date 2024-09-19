package com.Test.Tester1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {


    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        System.out.println("hallo1");
        return "adminDashboard";  // adminDashboard.html
    }

    @GetMapping("/user/home")
    public String userHome() {
        System.out.println("hallo2");
        return "userHome";  // userHome.html
    }

    @GetMapping("/start")
    public String startPage() {
        System.out.println("hallo3");
        return "start";  // start.html
    }

    @GetMapping("/login")
    public String loginPage() {
        System.out.println("hallo4");
        return "login";  // login.html
    }
}
