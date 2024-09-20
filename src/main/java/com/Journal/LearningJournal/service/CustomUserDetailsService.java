package com.Journal.LearningJournal.service;

import com.Journal.LearningJournal.model.Users;
import com.Journal.LearningJournal.repositories.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRespository userRespository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRespository.findByEmail(username);
        if (users == null) {
            throw new UsernameNotFoundException("Username nicht gefunden");
        }

        return new CustomUserDetail (users);

    }
}
