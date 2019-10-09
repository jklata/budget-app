package pl.jklata.budgetapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jklata.budgetapp.domain.Transaction;
import pl.jklata.budgetapp.repository.TransactionRepository;
import pl.jklata.budgetapp.service.TransactionService;

@Controller
public class NavController {

    private TransactionRepository transactionRepository;
    private TransactionService transactionService;

    @Autowired
    public NavController(TransactionRepository transactionRepository, TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
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
    public String getAddTransaction(Model model) {

        model.addAttribute("transaction", new Transaction());
        return "add-transaction";
    }

    @PostMapping
    @RequestMapping({"/addTransactionToList"})
    public String addTransactionToList(@ModelAttribute Transaction transaction){
        transactionService.save(transaction);
//        final Model transactions =
//                model.addAttribute("transactions", transactionRepository.findAll());
        return "redirect:/addTransaction";
    }


//    public String saveOrUpdateTransaction(@ModelAttribute Transaction transaction){
//        Transaction savedTransaction = transactionService.save(transaction);
//    }
}
