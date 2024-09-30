package com.Test.Tester1;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.model.Klassen;
import com.Test.Tester1.model.Themen;
import com.Test.Tester1.model.Vortragsthema;
import com.Test.Tester1.repository.ClassRepository;
import com.Test.Tester1.repository.TopicRepository;
import com.Test.Tester1.repository.UserRepository;
import com.Test.Tester1.repository.VortragsthemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class TopicManagementController {

    @Autowired
    private VortragsthemaRepository vortragsthemaRepository;

    @Autowired
    private TopicRepository kategorieRepository;

    @Autowired
    private final ClassRepository klasseRepository;

    @Autowired
    private UserRepository benutzerRepository;

    @Autowired
    public TopicManagementController(ClassRepository klasseRepository) {
        this.klasseRepository = klasseRepository;
    }

    @GetMapping("/admin/topicmanagement")
    public String showAllTopics(Model model) {

        List<Klassen> klassen = klasseRepository.findAll();
        List<Themen> kategorien = kategorieRepository.findAll();


        // Alle Vortragsthemen abrufen
        List<Vortragsthema> allTopics = vortragsthemaRepository.findAll();

        // Vortragsthemen an das Model übergeben
        model.addAttribute("topics", allTopics);
        model.addAttribute("klassen", klassen);
        model.addAttribute("kategorien", kategorien);

        return "topicmanagement";  // Gibt die HTML-Seite topicmanagement.html zurück
    }

    @PostMapping("/admin/assignTopics")
    public String assignTopicsToClass(@RequestParam("klassenid") Long klasseID, @RequestParam("kategorieid") Long kategorieID) {
        // Hole alle Benutzer der ausgewählten Klasse
        List<Benutzer> benutzerListe = benutzerRepository.findByKlassen_Klassenid(klasseID);

        // Hole alle Vortragsthemen der ausgewählten Kategorie
        List<Vortragsthema> vortragsthemen = vortragsthemaRepository.findByThemen_Kategorieid(kategorieID);

        // Erstelle ein Random-Objekt für die zufällige Auswahl
        Random random = new Random();

        // Weise allen Benutzern der Klasse ein zufälliges Thema zu
        for (Benutzer benutzer : benutzerListe) {
            if (!vortragsthemen.isEmpty()) {  // Überprüfen, ob die Liste nicht leer ist
                int randomIndex = random.nextInt(vortragsthemen.size());  // Wähle einen zufälligen Index
                Vortragsthema thema = vortragsthemen.get(randomIndex);    // Hol das zufällige Thema
                benutzer.setVortragsthema(thema);  // Weist dem Benutzer das zufällige Vortragsthema zu
                benutzerRepository.save(benutzer); // Speichert den Benutzer mit dem zugewiesenen Thema
            }
        }

        // Leite nach der Zuweisung wieder zur Verwaltung zurück
        return "redirect:/admin/topicmanagement";
    }

    @GetMapping("/admin/classManagementTopics")
    public String showClassManagement(Model model) {
        List<Klassen> klassen = klasseRepository.findAll();
        model.addAttribute("klassen", klassen);
        return "classManagementTopics"; // Zeigt die separate View für Klassenverwaltung
    }

    @PostMapping("/admin/classManagementTopics")
    public String displayUsersByClass(@RequestParam Long klassenid, Model model) {
        // Benutzer in der ausgewählten Klasse abrufen
        List<Benutzer> benutzerListe = benutzerRepository.findByKlassen_Klassenid(klassenid);

        // Thema für jeden Benutzer abrufen
        Map<Long, String> benutzerThemen = new HashMap<>();
        for (Benutzer benutzer : benutzerListe) {
            Vortragsthema thema = benutzer.getVortragsthema();
            if (thema != null) {
                benutzerThemen.put(benutzer.getBenutzerid(), thema.getThema()); // Thema zuweisen
            } else {
                benutzerThemen.put(benutzer.getBenutzerid(), "Kein Thema zugewiesen"); // Kein Thema
            }
        }

        model.addAttribute("benutzerThemen", benutzerThemen);
        model.addAttribute("benutzerListe", benutzerListe); // Benutzer zur Anzeige
        model.addAttribute("klassen", klasseRepository.findAll()); // Klassen erneut hinzufügen
        return "classManagementTopics"; // Zur neuen View zurückkehren
    }

    @PostMapping("/admin/topicdelete/{id}")
    public String deleteTopic(@PathVariable("id") Long themaid) {
        vortragsthemaRepository.deleteById(themaid);
        return "redirect:/admin/topicmanagement";
    }
}
