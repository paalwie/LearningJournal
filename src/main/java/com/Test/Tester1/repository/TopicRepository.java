package com.Test.Tester1.repository;

import com.Test.Tester1.model.Themen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Themen, Long> {

    Themen findByKategorieid(Long kategorieid);

}
