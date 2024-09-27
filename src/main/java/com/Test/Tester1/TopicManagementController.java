package com.Test.Tester1;

import com.Test.Tester1.model.Vortragsthema;
import com.Test.Tester1.repository.TopicRepository;
import com.Test.Tester1.repository.VortragsthemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class TopicManagementController {

    @Autowired
    private VortragsthemaRepository vortragsthemaRepository;

    @Autowired
    private TopicRepository kategorieRepository;

    @GetMapping("/admin/topicmanagement")
    public String showAllTopics(Model model) {
        // Alle Vortragsthemen abrufen
        List<Vortragsthema> allTopics = vortragsthemaRepository.findAll();

        // Vortragsthemen an das Model übergeben
        model.addAttribute("topics", allTopics);

        return "topicmanagement";  // Gibt die HTML-Seite topicmanagement.html zurück
    }
}
