package com.Test.Tester1.service;

import com.Test.Tester1.model.Klassen;
import com.Test.Tester1.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KlassenService {

    private final ClassRepository klasseRepository;

    public KlassenService(ClassRepository klasseRepository) {
        this.klasseRepository = klasseRepository;
    }

    public List<Klassen> getAllKlassen() {
        return klasseRepository.findAll();
    }
}