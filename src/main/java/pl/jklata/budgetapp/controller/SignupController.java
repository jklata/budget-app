package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jklata.budgetapp.dto.UserDto;
import pl.jklata.budgetapp.service.UserService;

import javax.validation.Valid;

@Slf4j
@Controller
public class SignupController {

    private final UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signup(Model model) {

        model.addAttribute("userDto", new UserDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute UserDto userDto, BindingResult binding, Model model) {

        if (binding.hasErrors()) {
            log.info("Validation errors when saving user: " + binding.getAllErrors());
            model.addAttribute(userDto);
            return "signup";
        }

        userService.signUpNewUser(userDto);
        return "redirect:/login";
    }
}
