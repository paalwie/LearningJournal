package com.Test.Tester1.repository;

import com.Test.Tester1.model.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Benutzer, Long> {
    Benutzer findByBenutzername(String benutzername);
}
