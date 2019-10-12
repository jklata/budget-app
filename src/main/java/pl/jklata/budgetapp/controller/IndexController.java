package pl.jklata.budgetapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    @GetMapping({"", "/", "/index"})
    public String getIndex() {
        return "index";
    }

    @GetMapping({"/wallets"})
    public String getWallets() {
        return "wallets";
    }

    @GetMapping({"/budgets"})
    public String getBudgets() {
        return "budgets";
    }

    @GetMapping({"/categories"})
    public String getCategories() {
        return "categories";
    }

    @GetMapping({"/reports"})
    public String getReports() {
        return "reports";
    }


}
