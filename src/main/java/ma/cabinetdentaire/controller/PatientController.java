package ma.cabinetdentaire.controller;

import ma.cabinetdentaire.entity.AntecedentMedicale;
import ma.cabinetdentaire.entity.DossierMedicale;
import ma.cabinetdentaire.entity.Patient;
import ma.cabinetdentaire.entity.Personne;
import ma.cabinetdentaire.services.DossierMedicalsService;
import ma.cabinetdentaire.services.PatientService;
import ma.cabinetdentaire.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private DossierMedicalsService dossierMedicalsService;
    @Autowired
    private PersonService personService;

    @GetMapping("/patients")
    public String getAllPatients(Model model) {
        List<Patient> patients = patientService.findAll();
        model.addAttribute("patients", patients);
        Patient patient = new Patient();
        patient.setPersonne(new Personne());
        patient.setDossierMedicals(new DossierMedicale());

        // Initialize antecedentMedicals as an empty string
        patient.setAntecedentMedicals("");

        model.addAttribute("patient", patient);
        return "patients";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.findById(id);
        return patient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/patients")
    public String createPatient(@ModelAttribute Patient patient) {
        DossierMedicale dossierMedicals = patient.getDossierMedicals();
        Personne personne = patient.getPersonne();

        // Save the DossierMedicale and Personne before saving the Patient
        dossierMedicalsService.save(dossierMedicals);
        personService.save(personne);

        patientService.save(patient);
        return "redirect:/patients";
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        Optional<Patient> patientOptional = patientService.findById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            patient.setMutuelle(patientDetails.getMutuelle());
            patient.setGroupSanguin(patientDetails.getGroupSanguin());
            patient.setAntecedentMedicals(patientDetails.getAntecedentMedicals());
            patient.setDossierMedicals(patientDetails.getDossierMedicals());

            // Save the DossierMedicale and Personne before saving the Patient
            dossierMedicalsService.save(patient.getDossierMedicals());
            personService.save(patient.getPersonne());

            return ResponseEntity.ok(patientService.save(patient));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
