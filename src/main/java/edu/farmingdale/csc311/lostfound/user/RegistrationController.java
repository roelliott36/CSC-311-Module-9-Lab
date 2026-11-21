package edu.farmingdale.csc311.lostfound.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

        @GetMapping("/register")
        public String showRegistrationForm() {
            return "register";
        }

    public RegistrationController(AppUserRepository userRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        if (username.length() < 3) {
            model.addAttribute("error",
                    "Username must be at least 3 characters.");
            return "register";
        }

        if (password.length() < 6) {
            model.addAttribute("error",
                    "Password must be at least 6 characters.");
            return "register";
        }

        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error",
                    "Username is already taken.");
            return "register";
        }

        String hashedPassword = passwordEncoder.encode(password);

        AppUser user = new AppUser(username, hashedPassword);

        userRepository.save(user);

        return "redirect:/login?registered";
    }

}


