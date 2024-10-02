package com.Test.Tester1.service;

import com.Test.Tester1.model.Vortragsthema;
import com.Test.Tester1.repository.VortragsthemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VortragsthemaService {

    private final VortragsthemaRepository vortragsthemaRepository;

    public VortragsthemaService(VortragsthemaRepository vortragsthemaRepository) {
        this.vortragsthemaRepository = vortragsthemaRepository;
    }

    public List<Vortragsthema> getAllVortragsthemen() {
        return vortragsthemaRepository.findAll();
    }
}
