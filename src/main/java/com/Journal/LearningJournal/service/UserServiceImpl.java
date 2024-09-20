package com.Journal.LearningJournal.service;

import com.Journal.LearningJournal.dto.UserDto;
import com.Journal.LearningJournal.model.Users;
import com.Journal.LearningJournal.repositories.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{



    @Autowired
    private UserRespository userRespository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void save(UserDto userDto) {
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        Users user = new Users(userDto.getEmail(), userDto.getFullname(), passwordEncoder.encode(userDto.getPassword())  ,userDto.getRole())  ;
        userRespository.save(user);
    }
}
