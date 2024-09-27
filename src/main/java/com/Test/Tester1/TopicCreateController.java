package com.Test.Tester1;

import com.Test.Tester1.model.Themen;
import com.Test.Tester1.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Test.Tester1.model.Vortragsthema;
import com.Test.Tester1.repository.VortragsthemaRepository;

import java.util.List;

@Controller
public class TopicCreateController {

    @Autowired
    private VortragsthemaRepository vortragsthemaRepository;

    @Autowired
    private TopicRepository kategorieRepository;

    // Zeigt das Formular zum Hinzufügen eines neuen Vortragsthemas an
    @GetMapping("/admin/topiccreate")
    public String showCreateTopicForm(Model model) {
        // Ein leeres Vortragsthema-Objekt übergeben
        model.addAttribute("topic", new Vortragsthema());

        // Lade die Kategorien für das Dropdown-Menü
        List<Themen> kategorien = kategorieRepository.findAll();
        model.addAttribute("kategorien", kategorien);

        return "topiccreate";  // Gibt das Formular für das Erstellen eines Vortragsthemas zurück
    }

    // Speichert das neue Vortragsthema
    @PostMapping("/admin/topiccreate")
    public String createNewTopic(@ModelAttribute Vortragsthema vortragsthema) {
        // Das neue Vortragsthema in die Datenbank speichern
        vortragsthemaRepository.save(vortragsthema);
        return "redirect:/admin/topicmanagement";  // Nach dem Speichern zur Themenverwaltung zurückkehren
    }
}
