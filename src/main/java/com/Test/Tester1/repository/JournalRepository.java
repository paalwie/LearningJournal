package com.Test.Tester1.repository;

import com.Test.Tester1.model.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {
    Page<Journal> findByBenutzerid(Long benutzerid, Pageable pageable);

    // Einträge eines Benutzers filtern nach dem Titel
    Page<Journal> findByBenutzeridAndTitelContainingIgnoreCase(Long benutzerId, String titel, Pageable pageable);
   // Page<Journal> findByBenutzerid(Long benutzerid, Pageable pageable);
}