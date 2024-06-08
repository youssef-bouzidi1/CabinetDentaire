package ma.cabinetdentaire.controller;

import ma.cabinetdentaire.entity.*;
import ma.cabinetdentaire.services.DossierMedicalsService;
import ma.cabinetdentaire.services.PatientService;
import ma.cabinetdentaire.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/patients/{id}")
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

    @PutMapping("/patients/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Patient> patientOptional = patientService.findById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();

            // Check for each field if it is present in the updates map, then update the patient object
            if (updates.containsKey("mutuelle")) {
                patient.setMutuelle(Mutuelle.valueOf((String) updates.get("mutuelle")));
            }
            if (updates.containsKey("groupSanguin")) {
                patient.setGroupSanguin(GroupSanguin.valueOf((String) updates.get("groupSanguin")));
            }
            if (updates.containsKey("antecedentMedicals")) {
                patient.setAntecedentMedicals((String) updates.get("antecedentMedicals"));
            }
            if (updates.containsKey("dossierMedicals")) {
                Map<String, Object> dossierUpdates = (Map<String, Object>) updates.get("dossierMedicals");
                DossierMedicale dossierMedicale = patient.getDossierMedicals();
                if (dossierMedicale == null) {
                    dossierMedicale = new DossierMedicale();
                }
                if (dossierUpdates.containsKey("numDossier")) {
                    dossierMedicale.setNumDossier((String) dossierUpdates.get("numDossier"));
                }
                // Add any other fields of DossierMedicale if needed
                patient.setDossierMedicals(dossierMedicale);
                dossierMedicalsService.save(dossierMedicale);
            }
            if (updates.containsKey("personne")) {
                Map<String, Object> personneUpdates = (Map<String, Object>) updates.get("personne");
                Personne personne = patient.getPersonne();
                if (personne == null) {
                    personne = new Personne();
                }
                if (personneUpdates.containsKey("nom")) {
                    personne.setNom((String) personneUpdates.get("nom"));
                }
                if (personneUpdates.containsKey("prenom")) {
                    personne.setPrenom((String) personneUpdates.get("prenom"));
                }
                if (personneUpdates.containsKey("adresse")) {
                    personne.setAdresse((String) personneUpdates.get("adresse"));
                }
                if (personneUpdates.containsKey("telephone")) {
                    personne.setTelephone((String) personneUpdates.get("telephone"));
                }
                if (personneUpdates.containsKey("email")) {
                    personne.setEmail((String) personneUpdates.get("email"));
                }
                if (personneUpdates.containsKey("sexe")) {
                    personne.setSexe((String) personneUpdates.get("sexe"));
                }
                if (personneUpdates.containsKey("dateNaissance")) {
                    personne.setDateNaissance((LocalDate) personneUpdates.get("dateNaissance"));
                }
                // Add any other fields of Personne if needed
                patient.setPersonne(personne);
                personService.save(personne);
            }

            return ResponseEntity.ok(patientService.save(patient));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
