package ma.cabinetdentaire.controller;

import ma.cabinetdentaire.entity.Consultation;
import ma.cabinetdentaire.entity.DossierMedicale;
import ma.cabinetdentaire.entity.Facture;
import ma.cabinetdentaire.entity.TypeConsultation;
import ma.cabinetdentaire.services.ConsultationService;
import ma.cabinetdentaire.services.DossierMedicalsService;
import ma.cabinetdentaire.services.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private DossierMedicalsService dossierMedicaleService;

    @Autowired
    private FactureService factureService;

    @GetMapping
    public String getAllConsultations(Model model) {
        List<Consultation> consultations = consultationService.findAll();
        List<DossierMedicale> dossiersMedicales = dossierMedicaleService.findAll();
        Consultation consultation = new Consultation();

        for (Consultation c : consultations) {
            List<Facture> factures = c.getFactures();
            if (factures == null) {
                factures = new ArrayList<>();
                c.setFactures(factures);
            }
        }
        model.addAttribute("consultations", consultations);
        model.addAttribute("dossiersMedicales", dossiersMedicales);
        model.addAttribute("consultation", consultation);
        return "/consultations";
    }

    @GetMapping("/{id}")
    public String getConsultationById(@PathVariable Long id, Model model) {
        Optional<Consultation> consultation = consultationService.findById(id);
        if (consultation.isPresent()) {
            Consultation consultationEntity = consultation.get();
            List<Facture> factures = consultationEntity.getFactures();
            model.addAttribute("consultation", consultationEntity);
            model.addAttribute("factures", factures);
            return "treatment-details";
        } else {
            return "redirect:/consultations";
        }
    }

    @PostMapping
    public String createConsultation(@ModelAttribute("consultation") Consultation consultation) {
        if (consultation.getTypeConsultation() == null) {
            consultation.setTypeConsultation(TypeConsultation.TEST);  // Set a default value
        }
        Consultation savedConsultation = consultationService.save(consultation);

        List<Facture> factures = consultation.getFactures();
        if (factures != null) {
            for (Facture facture : factures) {
                facture.setConsultation(savedConsultation);
                double montantRestant = facture.getMontantTotale() - facture.getMontantPaye();
                facture.setMontantRestant(montantRestant);
                factureService.save(facture);
            }
        }

        return "redirect:/consultations";
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consultation> updateConsultation(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Consultation> consultationOptional = consultationService.findById(id);
        if (consultationOptional.isPresent()) {
            Consultation consultation = consultationOptional.get();

            // Update Consultation fields if they are present in the updates map
            if (updates.containsKey("dateConsultation")) {
                Object dateConsultation = updates.get("dateConsultation");
                if (dateConsultation instanceof String) {
                    consultation.setDateConsultation(LocalDate.parse((String) dateConsultation));
                }
            }
            if (updates.containsKey("typeConsultation")) {
                Object typeConsultation = updates.get("typeConsultation");
                if (typeConsultation instanceof String) {
                    consultation.setTypeConsultation(TypeConsultation.valueOf((String) typeConsultation));
                }
            }

            // Update associated DossierMedicale if it's present in the updates map
            if (updates.containsKey("dossierMedicale")) {
                Object dossierMedicaleObj = updates.get("dossierMedicale");
                if (dossierMedicaleObj instanceof String) {
                    Long dossierId = Long.parseLong((String) dossierMedicaleObj);
                    DossierMedicale dossierMedicale = dossierMedicaleService.findById(dossierId)
                            .orElse(new DossierMedicale());
                    // Set the updated dossierMedicale to the consultation
                    consultation.setDossierMedicale(dossierMedicale);
                }
            }

            // Update associated Facture entities
            if (updates.containsKey("factures")) {
                Object facturesObj = updates.get("factures");
                if (facturesObj instanceof List) {
                    List<Map<String, Object>> facturesUpdates = (List<Map<String, Object>>) facturesObj;
                    List<Facture> existingFactures = consultation.getFactures();
                    Map<Long, Facture> existingFacturesMap = existingFactures.stream()
                            .collect(Collectors.toMap(Facture::getId, facture -> facture));

                    for (Map<String, Object> factureUpdate : facturesUpdates) {
                        Long factureId = factureUpdate.containsKey("id") ? Long.parseLong((String) factureUpdate.get("id")) : null;
                        Facture facture;
                        if (factureId != null && existingFacturesMap.containsKey(factureId)) {
                            facture = existingFacturesMap.get(factureId);
                        } else if (existingFactures.size() == 1) { // If there's only one existing Facture
                            facture = existingFactures.get(0); // Use the existing Facture
                        } else {
                            facture = new Facture();
                            facture.setConsultation(consultation);
                        }

                        Double montantTotale = facture.getMontantTotale();
                        Double montantPaye = facture.getMontantPaye();
                        Double montantRestant = facture.getMontantRestant();

                        if (factureUpdate.containsKey("montantTotale")) {
                            montantTotale = Double.parseDouble((String) factureUpdate.get("montantTotale"));
                            facture.setMontantTotale(montantTotale);
                        }
                        if (factureUpdate.containsKey("montantPaye")) {
                            montantPaye = Double.parseDouble((String) factureUpdate.get("montantPaye"));
                            facture.setMontantPaye(montantPaye);
                        }
                        if (factureUpdate.containsKey("montantRestant")) {
                            montantRestant = Double.parseDouble((String) factureUpdate.get("montantRestant"));
                            facture.setMontantRestant(montantRestant);
                        }

                        // Recalculate montantRestant if montantTotale or montantPaye is updated
                        if (factureUpdate.containsKey("montantTotale") || factureUpdate.containsKey("montantPaye")) {
                            montantRestant = montantTotale - montantPaye;
                            facture.setMontantRestant(montantRestant);
                        }

                        existingFacturesMap.put(facture.getId(), facture);
                    }

                    List<Facture> updatedFactures = new ArrayList<>(existingFacturesMap.values());

                    // Save all updated factures
                    factureService.saveAll(updatedFactures);

                    // Set the updated factures to the consultation
                    consultation.setFactures(updatedFactures);
                }
            }

            // Save the updated consultation
            consultationService.save(consultation);
            return ResponseEntity.ok(consultation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public String deleteConsultation(@PathVariable Long id) {
        consultationService.deleteById(id);
        return "redirect:/consultations";
    }

    @ModelAttribute("typeConsultations")
    public TypeConsultation[] getTypeConsultations() {
        return TypeConsultation.values();
    }
}
