package com.Test.Tester1;

import com.Test.Tester1.model.Klassen;
import com.Test.Tester1.model.Themen;
import com.Test.Tester1.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // 1. Kategorieverwaltung anzeigen
    @GetMapping("/categorymanagement")
    public String showCategoryManagement(Model model) {
        // Alle Kategorien aus der Datenbank abrufen
        List<Themen> categoryList = categoryRepository.findAll();
        System.out.println("Was in der DB steht bei Kategorien:");
        System.out.println(categoryList);
        model.addAttribute("categoryList", categoryList);
        return "categorymanagement"; // Zeigt die categorymanagement.html Seite
    }

    // 2. Neue Kategorie hinzufügen
    @PostMapping("/category/add")
    public String addCategory(@RequestParam String kategoriename, @RequestParam String kategoriebeschreibung) {
        Themen newCategory = new Themen();
        newCategory.setKategoriename(kategoriename);
        newCategory.setKategoriebeschreibung(kategoriebeschreibung);
        newCategory.setKategorieid(getNextCategoryId());
        categoryRepository.save(newCategory);
        return "redirect:/admin/categorymanagement"; // Leitet zurück zur Verwaltung nach dem Hinzufügen
    }

    private Long getNextCategoryId() {
        Optional<Themen> maxThemen = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "kategorieid")).stream().findFirst();
        return maxThemen.map(themen -> themen.getKategorieid() + 1).orElse(1L); // Falls keine Benutzer existieren, beginne bei 1
    }

    // 3. Bearbeitungsseite anzeigen
    @GetMapping("/category/edit/{id}")
    public String showEditCategory(@PathVariable Long id, Model model) {
        Themen category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Kategorie-ID: " + id));
        model.addAttribute("category", category);
        return "categoryedit"; // Zeigt die categoryedit.html Seite
    }

    // 4. Kategorie bearbeiten (nach dem Absenden des Formulars)
    @PostMapping("/category/update/{id}")
    public String updateCategory(@PathVariable Long id, @RequestParam String kategoriename, @RequestParam String kategoriebeschreibung) {
        Themen category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Kategorie-ID: " + id));
        category.setKategoriename(kategoriename);
        category.setKategoriebeschreibung(kategoriebeschreibung);
        categoryRepository.save(category);
        return "redirect:/admin/categorymanagement"; // Leitet nach der Aktualisierung zurück zur Verwaltung
    }

    // 5. Kategorie löschen
    @PostMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/categorymanagement"; // Leitet nach dem Löschen zurück zur Verwaltung
    }
}