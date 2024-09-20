package com.Test.Tester1.repository;

import com.Test.Tester1.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {
    List<Journal> findByBenutzerid(Long benutzerid);
}