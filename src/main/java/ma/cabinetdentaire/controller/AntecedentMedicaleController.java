package ma.cabinetdentaire.controller;

import ma.cabinetdentaire.entity.AntecedentMedicale;
import ma.cabinetdentaire.services.AntecedentMedicaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/antecedents")
public class AntecedentMedicaleController {

    @Autowired
    private AntecedentMedicaleService antecedentMedicaleService;

    @GetMapping
    public List<AntecedentMedicale> getAllAntecedents() {
        return antecedentMedicaleService.getAllAntecedents();
    }

    @GetMapping("/{id}")
    public AntecedentMedicale getAntecedentById(@PathVariable Long id) {
        return antecedentMedicaleService.getAntecedentById(id);
    }

    @PostMapping
    public AntecedentMedicale createAntecedent(@RequestBody AntecedentMedicale antecedentMedicale) {
        return antecedentMedicaleService.createAntecedent(antecedentMedicale);
    }

    @PutMapping("/{id}")
    public AntecedentMedicale updateAntecedent(@PathVariable Long id, @RequestBody AntecedentMedicale antecedentMedicale) {
        return antecedentMedicaleService.updateAntecedent(id, antecedentMedicale);
    }

    @DeleteMapping("/{id}")
    public void deleteAntecedent(@PathVariable Long id) {
        antecedentMedicaleService.deleteAntecedent(id);
    }
}
