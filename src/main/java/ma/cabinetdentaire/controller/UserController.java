package ma.cabinetdentaire.controller;

import ma.cabinetdentaire.entity.Utilisateur;
import ma.cabinetdentaire.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/utilisateurs")
public class UserController {

    @Autowired
    private UserService UserService;

    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return UserService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Optional<Utilisateur> utilisateur = UserService.findById(id);
        return utilisateur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return UserService.save(utilisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateurDetails) {
        Optional<Utilisateur> utilisateurOptional = UserService.findById(id);
        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            utilisateur.setUsername(utilisateurDetails.getUsername());
            utilisateur.setPassword(utilisateurDetails.getPassword());
            utilisateur.setRole(utilisateurDetails.getRole());
            utilisateur.setPersonne(utilisateurDetails.getPersonne());
            return ResponseEntity.ok(UserService.save(utilisateur));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        UserService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
