package pl.jklata.budgetapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavController {


    @RequestMapping({"", "/", "/index"})
    public String getIndex() {
        return "index";
    }

    @RequestMapping({"/transactions"})
    public String getTransactions() {
        return "transactions";
    }

    @RequestMapping({"/wallets"})
    public String getWallets() {
        return "wallets";
    }

    @RequestMapping({"/budgets"})
    public String getBudgets() {
        return "budgets";
    }

    @RequestMapping({"/categories"})
    public String getCategories() {
        return "categories";
    }

    @RequestMapping({"/reports"})
    public String getReports() {
        return "reports";
    }

    @RequestMapping({"/addTransaction"})
    public String getAddTransaction() {
        return "add-transaction";
    }
}
