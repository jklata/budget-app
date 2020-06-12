package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.service.SigninService;

import java.util.Collections;

/**
 * @author Jakub Klata, Pentacomp Systemy Informatyczne S.A.
 */
@Slf4j
@Controller
public class SigninController {

    SigninService signinService;

    @Autowired
    public SigninController(SigninService signinService) {
        this.signinService = signinService;
    }

    @GetMapping("/signin")
    public String signin(Model model) {

        model.addAttribute("user", new User());
        return "signin";
    }

    @PostMapping("/signin")
    public String signin(@ModelAttribute User user) {

        signinService.saveNewUser(user);
        return "redirect:/login";
    }
}
