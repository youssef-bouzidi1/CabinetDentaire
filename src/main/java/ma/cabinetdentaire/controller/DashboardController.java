package ma.cabinetdentaire.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null) {
            System.out.println("Authentication: " + authentication); // Debug line
            if (authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    username = ((UserDetails) principal).getUsername();
                }
            }
        }

        System.out.println("Username: " + username); // Debug line
        model.addAttribute("username", username);
        return "dashboard";
    }

}
