package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jklata.budgetapp.domain.Account;
import pl.jklata.budgetapp.service.AccountService;

import javax.persistence.EntityExistsException;

@Slf4j
@Controller
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping({"/wallets"})
    public String getWallets(Model model) {

        model.addAttribute("wallet", new Account());
        model.addAttribute("wallets", accountService.findAll());

        return "wallets";
    }


    @PostMapping({"/addWallet"})
    public String addBudgetToDataBase(@ModelAttribute Account account) {

        try {
            Account savedAccount = accountService.save(account);
            log.debug("Wykonano 'save' na account o ID: " + savedAccount.getId().toString());

        } catch (EntityExistsException e) {
            System.out.println("Account już istnieje" + e);
        }
        return "redirect:/wallets";
    }
}
