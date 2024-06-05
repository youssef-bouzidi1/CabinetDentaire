package ma.cabinetdentaire.controller;

import ma.cabinetdentaire.entity.Consultation;
import ma.cabinetdentaire.entity.DossierMedicale;
import ma.cabinetdentaire.entity.Facture;
import ma.cabinetdentaire.entity.TypeConsultation;
import ma.cabinetdentaire.services.ConsultationService;
import ma.cabinetdentaire.services.DossierMedicalsService;
import ma.cabinetdentaire.services.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/treatments")
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
        return "treatments";
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
            return "redirect:/treatments";
        }
    }
    @PostMapping
    public String createConsultation(@ModelAttribute("consultation") Consultation consultation) {
        if (consultation.getTypeConsultation() == null) {
            consultation.setTypeConsultation(TypeConsultation.TEST);  // Set a default value
        }
        Consultation savedConsultation = consultationService.save(consultation);

        // Get the list of factures from the consultation
        List<Facture> factures = consultation.getFactures();
        if (factures != null) {
            for (Facture facture : factures) {
                facture.setConsultation(savedConsultation);
                factureService.save(facture);
            }
        }

        return "redirect:/treatments";
    }


    @PutMapping("/{id}")
    public String updateConsultation(@PathVariable Long id, @ModelAttribute("consultation") Consultation consultation) {
        Optional<Consultation> existingConsultation = consultationService.findById(id);
        if (consultation.getTypeConsultation() == null) {
            consultation.setTypeConsultation(TypeConsultation.TEST);
        }
        if (existingConsultation.isPresent()) {
            Consultation savedConsultation = consultationService.save(consultation);
            List<Facture> factures = savedConsultation.getFactures();
            // Update the properties of the Facture instances
            factureService.saveAll(factures);
            return "redirect:/treatments";
        } else {
            return "redirect:/treatments";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteConsultation(@PathVariable Long id) {
        consultationService.deleteById(id);
        return "redirect:/treatments";
    }

    @ModelAttribute("typeConsultations")
    public TypeConsultation[] getTypeConsultations() {
        return TypeConsultation.values();
    }
}