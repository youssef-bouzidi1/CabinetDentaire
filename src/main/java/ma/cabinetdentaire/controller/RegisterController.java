package ma.cabinetdentaire.controller;

import ma.cabinetdentaire.entity.Personne;
import ma.cabinetdentaire.entity.Utilisateur;
import ma.cabinetdentaire.services.PersonService;
import ma.cabinetdentaire.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {
    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("personne", new Personne());
        model.addAttribute("utilisateur",new Utilisateur());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute Utilisateur user, RedirectAttributes redirectAttributes) {
        try {
            Personne savedPersonne = personService.save(user.getPersonne());
            //String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(user.getPassword());
            user.setPersonne(savedPersonne);
            userService.save(user);
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            return "redirect:/register";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed. Please try again.");
            return "redirect:/register";
        }
    }
}

