package com.Journal.LearningJournal.controller;
import org.springframework.boot.Banner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;

import com.Journal.LearningJournal.LearningJournalApplication;
import com.Journal.LearningJournal.dto.UserDto;
import com.Journal.LearningJournal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    UserDetailsService userDetailsService;



    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("users", new UserDto());
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("users")UserDto userDto, Model model) {
        userService.save(userDto);
        model.addAttribute("message", "Register Successfully");
        return "register";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("user-page")
    public String userPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
                model.addAttribute("user", userDetails);
          return "user";
    }

    @GetMapping("admin-page")
    public String AdminPage (Model model, Principal principal) {
        return "admin";
    }


}
