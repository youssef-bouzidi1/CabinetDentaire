package ma.cabinetdentaire.controller;

import ma.cabinetdentaire.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model) {
        if (userService.validateUser(username, password)) {
            return "redirect:/dashboard"; // توجيه إلى الصفحة الرئيسية بعد تسجيل الدخول الناجح
        } else {
            model.addAttribute("error", "Invalid username or password. Please try again.");
            return "login"; // إعادة التوجيه إلى صفحة تسجيل الدخول إذا فشل تسجيل الدخول
        }
    }
}