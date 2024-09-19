package com.Test.Tester1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Erlaubt den Zugriff auf die Startseite ohne Authentifizierung
                        .requestMatchers("/start", "/").permitAll()
                        // Login-Seite und Logout-URL sind öffentlich
                        .requestMatchers("/login", "/logout").permitAll()
                        // Alle anderen Seiten müssen authentifiziert sein
                        .anyRequest().authenticated()
                )
                // Konfiguration für die Login-Seite
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)  // Weiterleitung nach erfolgreicher Anmeldung
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/start")  // Weiterleitung zur Startseite nach dem Logout
                        .permitAll()
                );

        return http.build();
    }
}
