package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import pl.jklata.budgetapp.dto.UserDto;
import pl.jklata.budgetapp.service.SignupService;

import javax.validation.Valid;

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
    public String signup(WebRequest request, Model model) {

        model.addAttribute("userDto", new UserDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute UserDto userDto, BindingResult binding, Model model) {

        if (binding.hasErrors()) {
            log.info("Validation errors when saving provider: " + binding.getAllErrors());
            model.addAttribute(userDto);
            return "signup";
        }

        signupService.signUpNewUser(userDto);
        return "redirect:/login";
    }
}
