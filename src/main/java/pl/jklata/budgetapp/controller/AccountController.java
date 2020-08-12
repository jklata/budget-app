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
            log.debug("Account has been saved. Account name:{}, id: {}", account.getName(), savedAccount.getId());

        } catch (EntityExistsException e) {
            log.error("Such account already exists" + e);
        }
        return "redirect:/wallets";
    }
}
