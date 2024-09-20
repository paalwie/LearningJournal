package com.Test.Tester1.service;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Benutzer findByBenutzername(String benutzername) {
        return userRepository.findByBenutzername(benutzername);
    }
}