package com.Test.Tester1;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, @org.jetbrains.annotations.NotNull Authentication authentication)
            throws IOException, ServletException {

        System.out.println("hallo");

        // Hole die Rollen des authentifizierten Benutzers
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();

        // Umleiten basierend auf der Rolle
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("/admin/dashboard");
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            response.sendRedirect("/user/home");
        } else {
            response.sendRedirect("/start"); // Fallback für unbekannte Rollen
        }
    }
}
