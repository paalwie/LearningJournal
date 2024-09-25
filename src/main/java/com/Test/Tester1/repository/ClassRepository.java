package com.Test.Tester1.repository;

import com.Test.Tester1.model.Klassen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ClassRepository extends JpaRepository<Klassen, Long> {

}
