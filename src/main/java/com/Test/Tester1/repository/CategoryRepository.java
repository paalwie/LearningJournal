package com.Test.Tester1.repository;

import com.Test.Tester1.model.Themen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CategoryRepository extends JpaRepository<Themen, Long> {
}