package ma.cabinetdentaire.controller;

import ma.cabinetdentaire.entity.Dentist;
import ma.cabinetdentaire.entity.Personne;
import ma.cabinetdentaire.entity.Utilisateur;
import ma.cabinetdentaire.services.DentistService;
import ma.cabinetdentaire.services.PersonService;
import ma.cabinetdentaire.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class DentistController {
    @Autowired
    private DentistService dentistService;
    @Autowired
    private UserService utilisateurService;
    @Autowired
    private PersonService personneService;

    @GetMapping("/dentists")
    public String getAllDentists(Model model) {
        List<Dentist> dentists = dentistService.findAll();
        model.addAttribute("dentists", dentists);
        Dentist dentist = new Dentist();
        dentist.setUtilisateur(new Utilisateur());
        model.addAttribute("dentist", dentist);
        return "dentists";
    }

    @GetMapping("/dentists/{id}")
    public ResponseEntity<Dentist> getDentistById(@PathVariable Long id) {
        Optional<Dentist> dentist = dentistService.findById(id);
        return dentist.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/dentists")
    public String createDentist(@ModelAttribute Dentist dentist) {
        Utilisateur utilisateur = dentist.getUtilisateur();
        Personne personne = utilisateur.getPersonne();

        // Save the Personne entity first
        if (personne != null) {
            personne = personneService.save(personne);
            utilisateur.setPersonne(personne);
        }

        // Save the Utilisateur entity
        utilisateur = utilisateurService.save(utilisateur);

        // Set the saved Utilisateur in the Dentist entity
        dentist.setUtilisateur(utilisateur);

        // Save the Dentist entity
        dentistService.save(dentist);

        return "redirect:/dentists";
    }

    @PutMapping("/dentists/{id}")
    public ResponseEntity<Dentist> updateDentist(@PathVariable Long id, @RequestBody Dentist dentistDetails) {
        Optional<Dentist> dentistOptional = dentistService.findById(id);
        if (dentistOptional.isPresent()) {
            Dentist dentist = dentistOptional.get();
            dentist.setDisponibilite(dentistDetails.getDisponibilite());
            dentist.setSpecialite(dentistDetails.getSpecialite());
            dentist.setAssurance(dentistDetails.getAssurance());
            dentist.setStatutActuel(dentistDetails.getStatutActuel());
            dentist.setUtilisateur(dentistDetails.getUtilisateur());

            utilisateurService.save(dentist.getUtilisateur());

            return ResponseEntity.ok(dentistService.save(dentist));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/dentists/{id}")
    public ResponseEntity<Void> deleteDentist(@PathVariable Long id) {
        dentistService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}