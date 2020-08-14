package pl.jklata.budgetapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    @GetMapping({"", "/", "/index"})
    public String getIndex() {
        return "index";
    }

    @GetMapping({"/login"})
    public String getLogin() {
        return "login";
    }
}
