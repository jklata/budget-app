package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jklata.budgetapp.dto.UserDto;
import pl.jklata.budgetapp.service.SignupService;

/**
 * @author Jakub Klata, Pentacomp Systemy Informatyczne S.A.
 */
@Slf4j
@Controller
public class SignupController {

    SignupService signupService;

    @Autowired
    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @GetMapping("/signup")
    public String signup(Model model) {

        model.addAttribute("userDto", new UserDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDto userDto) {

        signupService.saveNewUser(userDto);
        return "redirect:/login";
    }
}
