package com.Test.Tester1.service;

import com.Test.Tester1.model.Themen;
import com.Test.Tester1.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KategorieService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Themen findThemenById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}