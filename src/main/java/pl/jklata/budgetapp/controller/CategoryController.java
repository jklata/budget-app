package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.service.PaymentCategoryService;

import javax.persistence.EntityExistsException;

@Slf4j
@Controller
public class CategoryController {

    private PaymentCategoryService paymentCategoryService;

    public CategoryController(PaymentCategoryService paymentCategoryService) {
        this.paymentCategoryService = paymentCategoryService;
    }

    @GetMapping({"/categories"})
    public String getCategories(Model model) {

        model.addAttribute("paymentCategory", new PaymentCategory());
        model.addAttribute("paymentCategories", paymentCategoryService.findAll());

        return "categories";
    }


    @PostMapping({"/addCategory"})
    public String addCategoryToDataBase(@ModelAttribute PaymentCategory paymentCategory) {

        try {
            PaymentCategory savedPaymentCategory = paymentCategoryService.save(paymentCategory);
            log.debug("Category has been saved. Category name:{}, id: {}", paymentCategory.getName(), savedPaymentCategory.getId());

        } catch (EntityExistsException e) {
            log.error("Such category already exists" + e);
        }
        return "redirect:/categories";
    }
}
