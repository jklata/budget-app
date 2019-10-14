package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jklata.budgetapp.domain.Transaction;
import pl.jklata.budgetapp.service.AccountService;
import pl.jklata.budgetapp.service.BudgetService;
import pl.jklata.budgetapp.service.TransactionCategoryService;
import pl.jklata.budgetapp.service.TransactionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
public class TransactionController {


    private TransactionCategoryService transactionCategoryService;
    private TransactionService transactionService;
    private AccountService accountService;
    private BudgetService budgetService;

    @Autowired
    public TransactionController(TransactionCategoryService transactionCategoryService, TransactionService transactionService, AccountService accountService, BudgetService budgetService) {
        this.transactionCategoryService = transactionCategoryService;
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.budgetService = budgetService;
    }


    @GetMapping({"/transactions2"})
    public String getTransactions(Model model) {

        model.addAttribute("transactions", transactionService.findAll());
        return "transactions/transactions2";
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public String listTransactions(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Transaction> transactionPage = transactionService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("transactionPage", transactionPage);

        int totalPages = transactionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "transactions/transactions";
    }


    @GetMapping({"/{id}/show"})
    public String getTransactions(@PathVariable Long id, Model model) {

        model.addAttribute("transaction", transactionService.findById(id));
        return "transactions/transaction-show";
    }

    @GetMapping({"/addTransaction"})
    public String getAddTransaction(Model model) {

        model.addAttribute("transaction", new Transaction());
        log.debug("Utworzono nowy obiekt transakcji");

        model.addAttribute("transactionCategories", transactionCategoryService.findAll());
        model.addAttribute("budgets", budgetService.findAll());
        model.addAttribute("wallets", accountService.findAll());
        return "transactions/add-transaction";
    }

    @GetMapping("/{id}/update")
    public String updateTransaction(@PathVariable Long id, Model model) {
        model.addAttribute("transaction", transactionService.findById(id));

//        model.addAttribute("transactionCategories", transactionCategoryService.findAll());
//        model.addAttribute("budgets", budgetService.findAll());
//        model.addAttribute("wallets", accountService.findAll());

        log.debug("Request update na transakcji o ID: " + id.toString());
        return "transactions/add-transaction";
    }

    @GetMapping("/{id}/delete")
    public String deleteTransaction(@PathVariable Long id) {
        log.debug("Request delete na transakcji o ID: " + id.toString());
        transactionService.deleteById(id);
        return "redirect:/transactions";
    }


//    todo zmienic nazwe endpointu

    @PostMapping({"addTransactionToList"})
    public String addTransactionToDataBase(@ModelAttribute Transaction transaction, @RequestParam String transactionCategoryEach, @RequestParam String walletEach, @RequestParam String budgetEach) {

        log.debug("Przekazana transakcja ma id: " + transaction.getId());
        transaction.setTransactionCategory(transactionCategoryService.findByName(transactionCategoryEach));
        transaction.setAccount(accountService.findByName(walletEach));
        transaction.setBudget(budgetService.findByName(budgetEach));
        Transaction savedTransaction = transactionService.save(transaction);
        log.debug("Wykonano 'save' na transakcji o ID: " + savedTransaction.getId().toString());
        return "redirect:/transactions";
    }


}
