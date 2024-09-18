package com.Journal.LearningJournal;

import com.Journal.LearningJournal.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;  // Dein JPA-Repository, um User aus der DB zu laden

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(username);

        // Lade den Benutzer anhand des Benutzernamens aus der DB
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Get the user's role
        Rolle rolle = user.getRolle();

        // Convert Rolle to SimpleGrantedAuthority (assuming your Rolle has a getName() method)
        Collection<? extends GrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority(rolle.getRollenname()));

        System.out.println("User: " + user.getUsername() + " has role: " + rolle.getRollenname());

        // Mappe den User auf ein Spring Security UserDetails Objekt
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswort(),
                authorities
        );
    }
}
