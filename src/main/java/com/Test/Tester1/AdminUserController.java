package com.Test.Tester1;

import com.Test.Tester1.model.Benutzer;
import com.Test.Tester1.model.Klassen;
import com.Test.Tester1.repository.ClassRepository;
import com.Test.Tester1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller






@RequestMapping("/admin")  // Prefix für alle Admin-spezifischen Endpunkte
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassRepository classRepository;

    // Diese Methode wird aufgerufen, wenn du /admin/usermanagement aufrufst
    @GetMapping("/usermanagement")
    public String getAllUsers(
            @RequestParam(defaultValue = "benutzerid") String sortBy,
            Model model,
            @AuthenticationPrincipal UserDetails currentUser) {

        List<Benutzer> users = userRepository.findAll(Sort.by(sortBy));  // Sortierung basierend auf dem Parameter
        model.addAttribute("users", users);
        model.addAttribute("currentSort", sortBy);  // Aktuelle Sortierung zur View hinzufügen

        // Benutzername des eingeloggten Benutzers hinzufügen
        model.addAttribute("benutzername", currentUser.getUsername());

        return "usermanagement";
    }

    @GetMapping("/usercreate")
    public String showCreateUserForm(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        Benutzer newUser = new Benutzer();
        newUser.setBenutzerid(getNextBenutzerId());  // Automatische ID setzen

        List<Klassen> classes = classRepository.findAll();
        model.addAttribute("klassen", classes);

        model.addAttribute("newUser", newUser);  // Übergibt den Benutzer an das Template

        // Benutzername des eingeloggten Benutzers hinzufügen
        model.addAttribute("benutzername", currentUser.getUsername());

        return "usercreate";  // Zeigt das Formular zur Benutzererstellung
    }

    @PostMapping("/usercreate")
    public String createUser(@ModelAttribute Benutzer newUser, @RequestParam("klassen") Long klassenId) {
        if (newUser.getBenutzerid() == null) {
            newUser.setBenutzerid(getNextBenutzerId()); // Fallback falls Benutzer-ID nicht gesetzt
        }

        // Klasse aus der Datenbank laden
        Klassen selectedClass = classRepository.findById(klassenId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Klassen ID: " + klassenId));

        // Der neuen Benutzerinstanz die Klasse zuweisen
        newUser.setKlassen(selectedClass);

        // Optional: Passwort-Hashing hinzufügen
        userRepository.save(newUser);  // Speichert den neuen Benutzer in der Datenbank
        return "redirect:/admin/usermanagement";  // Leitet zurück zur Benutzerübersicht
    }

    public Long getNextBenutzerId() {
        Optional<Benutzer> maxBenutzer = userRepository.findAll(Sort.by(Sort.Direction.DESC, "benutzerid")).stream().findFirst();
        return maxBenutzer.map(benutzer -> benutzer.getBenutzerid() + 1).orElse(1L); // Falls keine Benutzer existieren, beginne bei 1
    }

    @PostMapping("/userdelete/{id}")
    public String deleteUser(@PathVariable("id") Long benutzerid) {
        userRepository.deleteById(benutzerid);  // Löscht den Benutzer anhand der ID
        return "redirect:/admin/usermanagement";  // Leitet nach dem Löschen zurück zur Benutzerübersicht
    }

    @GetMapping("/useredit/{id}")
    public String editUser(@PathVariable("id") Long benutzerid, Model model, @AuthenticationPrincipal UserDetails currentUser) {
        Benutzer user = userRepository.findById(benutzerid).orElseThrow(() -> new IllegalArgumentException("Ungültige Benutzer-ID:" + benutzerid));
        model.addAttribute("user", user);  // Benutzer-Daten werden an die View übergeben

        // Benutzername des eingeloggten Benutzers hinzufügen
        model.addAttribute("benutzername", currentUser.getUsername());

        return "useredit";  // Rendere die Seite useredit.html
    }

    @PostMapping("/userupdate/{id}")
    public String updateUser(@PathVariable("id") Long benutzerid, Benutzer updatedUser) {
        Benutzer existingUser = userRepository.findById(benutzerid)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Benutzer-ID:" + benutzerid));

        existingUser.setBenutzername(updatedUser.getBenutzername());
        existingUser.setRolleid(updatedUser.getRolleid());
        existingUser.setKlassenid(updatedUser.getKlassenid());
        existingUser.setPasswort(updatedUser.getPasswort());

        // Füge hier weitere Felder hinzu, falls nötig

        userRepository.save(existingUser);  // Speichern der aktualisierten Benutzerinformationen
        return "redirect:/admin/usermanagement";  // Zurück zur Benutzerübersicht
    }
}
