package com.Journal.LearningJournal.repositories;

import com.Journal.LearningJournal.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends JpaRepository<Users, Long> {

    Users findByEmail(String email) ;
}
