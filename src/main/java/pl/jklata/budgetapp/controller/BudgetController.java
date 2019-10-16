package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jklata.budgetapp.domain.Budget;
import pl.jklata.budgetapp.service.BudgetService;

import javax.persistence.EntityExistsException;

@Slf4j
@Controller
public class BudgetController {

    private BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping({"/budgets"})
    public String getBudgets(Model model) {

        model.addAttribute("budget", new Budget());
        model.addAttribute("budgets", budgetService.findAll());

        return "budgets";
    }


    @PostMapping({"/addBudget"})
    public String addBudgetToDataBase(@ModelAttribute Budget budget) {

        try {
            Budget savedBudget = budgetService.save(budget);
            log.debug("Wykonano 'save' na budżecie o ID: " + savedBudget.getId().toString());

        } catch (EntityExistsException e) {
            System.out.println("Budżet już istnieje" + e);
        }
        return "redirect:/budgets";
    }
}
