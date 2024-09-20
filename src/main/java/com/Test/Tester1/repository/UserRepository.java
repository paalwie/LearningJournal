package com.Test.Tester1.repository;

import com.Test.Tester1.model.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Benutzer, Long> {
    Benutzer findByBenutzername(String benutzername);
}
