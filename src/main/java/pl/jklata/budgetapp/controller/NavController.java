package pl.jklata.budgetapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jklata.budgetapp.domain.Transaction;
import pl.jklata.budgetapp.repository.TransactionRepository;

import java.util.List;

@Controller
public class NavController {

    private TransactionRepository transactionRepository;

    public NavController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndex() {
        return "index";
    }

    @RequestMapping({"/transactions"})
    public String getTransactions(Model model) {

        model.addAttribute("transactions", transactionRepository.findAll());

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
