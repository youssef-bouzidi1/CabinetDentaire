package ma.cabinetdentaire.controller;

import ma.cabinetdentaire.entity.Dentist;
import ma.cabinetdentaire.entity.Dentist;
import ma.cabinetdentaire.services.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Dentists")
public class DentistController {
    @Autowired
    private DentistService dentistService;
    @GetMapping
    public List<Dentist> getAllDentist(){return dentistService.findAll();}
    @GetMapping("/id")
    public ResponseEntity<Dentist> getDentistById(@PathVariable Long id){
        Optional<Dentist> dentist = dentistService.findById(id);
        return dentist.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public Dentist createDentist(@RequestBody Dentist dentist){return dentistService.save(dentist);}
    @PutMapping("/{id}")
    public ResponseEntity<Dentist> updateDentist(@PathVariable Long id, @RequestBody Dentist dentistDetails) {
        Optional<Dentist> dentistOptional = dentistService.findById(id);
        if (dentistOptional.isPresent()) {
            Dentist dentist = dentistOptional.get();
            dentist.setDisponibilite(dentistDetails.getDisponibilite());
            dentist.setSpecialite(dentistDetails.getSpecialite());
            return ResponseEntity.ok(dentistService.save(dentist));

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDentist(@PathVariable Long id) {
        dentistService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
