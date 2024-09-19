package com.Test.Tester1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private final DataSource dataSource;

    public CustomUserDetailsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Gib was aus!!!");
        logger.info("Versuche, Benutzer mit Username: {}", username);

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT benutzername, passwort, rolleid FROM benutzer WHERE benutzername = '" + username + "'";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (!resultSet.next()) {
                    throw new UsernameNotFoundException("Benutzer nicht gefunden: " + username);
                }

                String benutzername = resultSet.getString("benutzername");
                String passwort = resultSet.getString("passwort");
                int roleId = resultSet.getInt("rolleid");

                System.out.println("Benutzer: " + benutzername + " mit Rolle: " + roleId);

                System.out.println(roleId);

                // Definiere die Rolle basierend auf der roleId
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (roleId == 1) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                } else if (roleId == 2) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }

                return new org.springframework.security.core.userdetails.User(benutzername, passwort, authorities);
            }

        } catch (SQLException e) {
            throw new UsernameNotFoundException("Fehler beim Abrufen des Benutzers: " + e.getMessage());
        }
    }
}
