package ma.cabinetdentaire.controller;

import ma.cabinetdentaire.entity.Personne;
import ma.cabinetdentaire.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personnes")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Personne> getAllPersonnes() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personne> getPersonneById(@PathVariable Long id) {
        Optional<Personne> personne = personService.findById(id);
        return personne.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Personne createPersonne(@RequestBody Personne personne) {
        return personService.save(personne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personne> updatePersonne(@PathVariable Long id, @RequestBody Personne personneDetails) {
        Optional<Personne> personneOptional = personService.findById(id);
        if (personneOptional.isPresent()) {
            Personne personne = personneOptional.get();
            personne.setNom(personneDetails.getNom());
            personne.setPrenom(personneDetails.getPrenom());
            personne.setDateNaissance(personneDetails.getDateNaissance());
            return ResponseEntity.ok(personService.save(personne));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
