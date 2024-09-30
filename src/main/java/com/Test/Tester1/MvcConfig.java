package com.Test.Tester1;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("start");
        registry.addViewController("/change-password").setViewName("change-password");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/start").setViewName("start");
        registry.addViewController("/user/home").setViewName("userHome");
        registry.addViewController("/admin/dashboard").setViewName("adminDashboard");
        registry.addViewController("/access-denied").setViewName("accessDenied");
        registry.addViewController("/admin/admin2").setViewName("admin2");
        registry.addViewController("/user/user2").setViewName("user2");
        registry.addViewController("/logout").setViewName("start");
        registry.addViewController("user/journal").setViewName("userJournal");
        registry.addViewController("user/journalcreate").setViewName("journalcreate");
        registry.addViewController("user/journaledit").setViewName("journaledit");
        registry.addViewController("user/journalentry").setViewName("journalentry");
        registry.addViewController("/admin/usermanagement").setViewName("usermanagement");
        registry.addViewController("/admin/usercreate").setViewName("usercreate");
        registry.addViewController("/admin/useredit").setViewName("useredit");
        registry.addViewController("/admin/classmanagement").setViewName("classmanagement");
        registry.addViewController("/admin/categorymanagement").setViewName("categorymanagement");
        registry.addViewController("/admin/classedit").setViewName("classedit");
        registry.addViewController("/admin/categoryedit").setViewName("categoryedit");

    }
}