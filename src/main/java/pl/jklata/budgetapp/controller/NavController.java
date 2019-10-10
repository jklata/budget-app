package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jklata.budgetapp.domain.Transaction;
import pl.jklata.budgetapp.repository.TransactionCategoryRepository;
import pl.jklata.budgetapp.repository.TransactionRepository;
import pl.jklata.budgetapp.service.TransactionService;

@Slf4j
@Controller
public class NavController {


    private TransactionCategoryRepository transactionCategoryRepository;
    private TransactionService transactionService;

    @Autowired
    public NavController(TransactionCategoryRepository transactionCategoryRepository, TransactionService transactionService) {
        this.transactionCategoryRepository = transactionCategoryRepository;
        this.transactionService = transactionService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndex() {
        return "index";
    }

    @RequestMapping({"/transactions"})
    public String getTransactions(Model model) {

        model.addAttribute("transactions", transactionService.findAll());

        return "transactions";
    }

    @RequestMapping({"/{id}/show"})
    public String getTransactions(@PathVariable Long id, Model model) {

        model.addAttribute("transaction", transactionService.findById(id));
        return "transactionShow";
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
        log.debug("Utworzono nowy obiekt transakcji");
        model.addAttribute("transactionCategories", transactionCategoryRepository.findAll());
        return "add-transaction";
    }

    @RequestMapping("/{id}/update")
    public String updateTransaction(@PathVariable Long id, Model model) {
        model.addAttribute("transaction", transactionService.findById(id));
        log.debug("Request update na transakcji o ID: " + id.toString());
        return "add-transaction";
    }

    @RequestMapping("/{id}/delete")
    public String deleteTransaction(@PathVariable Long id, Model model) {
        log.debug("Request delete na transakcji o ID: " + id.toString());
//        model.addAttribute("transaction", transactionService.findById(id));
        transactionService.deleteById(id);
        return "add-transaction";
    }

    @PostMapping({"addTransactionToList"})
    public String addTransactionToList(@ModelAttribute Transaction transaction) {

        log.debug("przekazana transakcja ma id: " + transaction.getId());
        Transaction savedTransaction = transactionService.save(transaction);
        log.debug("Wykonano 'save' na transakcji o ID: " + savedTransaction.getId().toString());
//        return "redirect:/" + savedTransaction.getId() + "/show";
        return "redirect:/transactions";
    }


}
