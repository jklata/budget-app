package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jklata.budgetapp.domain.TransactionCategory;
import pl.jklata.budgetapp.service.TransactionCategoryService;

import javax.persistence.EntityExistsException;

@Slf4j
@Controller
public class CategoryController {

    private TransactionCategoryService transactionCategoryService;

    public CategoryController(TransactionCategoryService transactionCategoryService) {
        this.transactionCategoryService = transactionCategoryService;
    }

    @GetMapping({"/categories"})
    public String getCategories(Model model) {

        model.addAttribute("transactionCategory", new TransactionCategory());
        model.addAttribute("transactionCategories", transactionCategoryService.findAll());

        return "categories";
    }


    @PostMapping({"/addCategory"})
    public String addTransactionToList(@ModelAttribute TransactionCategory transactionCategory) {

        try {
            TransactionCategory savedTransactionCategory = transactionCategoryService.save(transactionCategory);
            log.debug("Wykonano 'save' na kategorii o ID: " + savedTransactionCategory.getId().toString());

        } catch (EntityExistsException e) {
            System.out.println("Rekord istnieje" + e);
        }
        return "redirect:/categories";
    }
}
