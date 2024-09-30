package com.Test.Tester1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Test.Tester1.model.Vortragsthema;

import java.util.List;

@Repository
public interface VortragsthemaRepository extends JpaRepository<Vortragsthema, Long> {
    // Standard CRUD-Methoden werden automatisch bereitgestellt
    List<Vortragsthema> findByThemen_Kategorieid(Long kategorieId);
}
