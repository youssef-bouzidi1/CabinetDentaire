package ma.cabinetdentaire.controller;

import ma.cabinetdentaire.services.FactureService;
import ma.cabinetdentaire.services.PatientService;
import ma.cabinetdentaire.entity.Facture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private FactureService factureService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        int totalPatients = patientService.findAll().size();
        double totalRevenue = factureService.findAll().stream()
                .mapToDouble(Facture::getMontantPaye)
                .sum();
        double accumulatedRevenue = factureService.findAll().stream()
                .mapToDouble(Facture::getMontantRestant)
                .sum();

        model.addAttribute("totalPatients", totalPatients);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("accumulatedRevenue", accumulatedRevenue);

        return "dashboard";
    }
}


