package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jklata.budgetapp.domain.Transaction;
import pl.jklata.budgetapp.service.TransactionCategoryService;
import pl.jklata.budgetapp.service.TransactionService;

@Slf4j
@Controller
public class TransactionController {


    private TransactionCategoryService transactionCategoryService;
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionCategoryService transactionCategoryService, TransactionService transactionService) {
        this.transactionCategoryService = transactionCategoryService;
        this.transactionService = transactionService;
    }


    @GetMapping({"/transactions"})
    public String getTransactions(Model model) {

        model.addAttribute("transactions", transactionService.findAll());

        return "transactions/transactions";
    }

    @GetMapping({"/{id}/show"})
    public String getTransactions(@PathVariable Long id, Model model) {

        model.addAttribute("transaction", transactionService.findById(id));
        return "transaction-show";
    }

    @GetMapping({"/addTransaction"})
    public String getAddTransaction(Model model) {

        model.addAttribute("transaction", new Transaction());
        log.debug("Utworzono nowy obiekt transakcji");
        model.addAttribute("transactionCategories", transactionCategoryService.findAll());
        return "transactions/add-transaction";
    }

    @GetMapping("/{id}/update")
    public String updateTransaction(@PathVariable Long id, Model model) {
        model.addAttribute("transaction", transactionService.findById(id));
        log.debug("Request update na transakcji o ID: " + id.toString());
        return "transactions/add-transaction";
    }

    @GetMapping("/{id}/delete")
    public String deleteTransaction(@PathVariable Long id) {
        log.debug("Request delete na transakcji o ID: " + id.toString());
        transactionService.deleteById(id);
        return "redirect:/transactions";
    }

    @PostMapping({"addTransactionToList"})
    public String addTransactionToList(@ModelAttribute Transaction transaction) {

        log.debug("Przekazana transakcja ma id: " + transaction.getId());
        Transaction savedTransaction = transactionService.save(transaction);
        log.debug("Wykonano 'save' na transakcji o ID: " + savedTransaction.getId().toString());
        return "redirect:/transactions";
    }


}
