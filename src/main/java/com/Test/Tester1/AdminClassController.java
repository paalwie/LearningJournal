package com.Test.Tester1;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.model.Klassen;
import com.Test.Tester1.repository.ClassRepository;
import com.Test.Tester1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminClassController {

    @Autowired
    private ClassRepository classRepository;

    // Zeigt die classmanagement-Seite an
    @GetMapping("/classmanagement")
    public String getClassManagement(Model model) {
        List<Klassen> classes = classRepository.findAll();  // Alle Klassen abrufen
        model.addAttribute("classes", classes);
        return "classmanagement";  // classmanagement.html anzeigen
    }

    // Neue Klasse hinzufügen
    @PostMapping("/class/add")
    public String addClass(@RequestParam String classname) {
        Klassen newClass = new Klassen();
        newClass.setKlassenname(classname);
        newClass.setKlassenid(getNextClassId());
        classRepository.save(newClass);
        return "redirect:/admin/classmanagement";  // Seite aktualisieren
    }

    public Long getNextClassId() {
        Optional<Klassen> maxKlassen = classRepository.findAll(Sort.by(Sort.Direction.DESC, "klassenid")).stream().findFirst();
        return maxKlassen.map(klassen -> klassen.getKlassenid() + 1).orElse(1L); // Falls keine Benutzer existieren, beginne bei 1
    }

    // Klasse löschen
    @PostMapping("/class/delete/{id}")
    public String deleteClass(@PathVariable Long id) {
        classRepository.deleteById(id);  // Klasse löschen
        return "redirect:/admin/classmanagement";
    }

    @GetMapping("/class/edit/{id}")
    public String editClass(@PathVariable Long id, Model model) {
        // Klasse anhand der ID finden
        Klassen classEntity = classRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Klassen ID: " + id));

        // Klasse in das Model packen, um sie im Template zu verwenden
        model.addAttribute("class", classEntity);
        return "classedit"; // Verweist auf classedit.html
    }

    @PostMapping("/class/update/{id}")
    public String updateClass(@PathVariable Long id, @RequestParam String klassenname) {
        // Klasse aus der Datenbank holen
        Klassen classEntity = classRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Klassen ID: " + id));

        // Klassennamen aktualisieren
        classEntity.setKlassenname(klassenname);

        // In der Datenbank speichern
        classRepository.save(classEntity);

        // Nach der Aktualisierung zur Klassenseite zurückkehren
        return "redirect:/admin/classmanagement";
    }
}
