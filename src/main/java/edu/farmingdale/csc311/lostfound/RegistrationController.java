package edu.farmingdale.csc311.lostfound;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import edu.farmingdale.csc311.lostfound.user.AppUser;
import edu.farmingdale.csc311.lostfound.user.AppUserRepository;
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

        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        String hashedPassword = passwordEncoder.encode(password);

        AppUser user = new AppUser(username, hashedPassword);

        userRepository.save(user);

        return "redirect:/login";
    }

}


