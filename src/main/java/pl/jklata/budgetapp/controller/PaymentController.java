package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.service.*;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@RequestMapping(value = "/payments")
@Controller
public class PaymentController {


    private PaymentCategoryService paymentCategoryService;
    private PaymentService paymentService;
    private AccountService accountService;
    private BudgetService budgetService;
    private CsvService csvService;

    @Autowired
    public PaymentController(PaymentCategoryService paymentCategoryService, PaymentService paymentService, AccountService accountService, BudgetService budgetService, CsvService csvService) {
        this.paymentCategoryService = paymentCategoryService;
        this.paymentService = paymentService;
        this.accountService = accountService;
        this.budgetService = budgetService;
        this.csvService = csvService;
    }

    @GetMapping(value = "")
    public String listPayments(
            ModelMap model, @SortDefault("id") Pageable pageable) {

        model.addAttribute("page", paymentService.findPaginated(pageable));

        return "payments/paymentList";
    }

    @GetMapping(value = "/{id}/show")
    public String getPayment(@PathVariable Long id, Model model) {

        model.addAttribute("payment", paymentService.findByIdForAuthUser(id));
        return "payments/paymentShow";
    }

    @GetMapping(value = "/add")
    public String getAddPayment(Model model) {

        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        payment.setIdForUser(paymentService.resolveNextIdForUser());
        model.addAttribute("payment", payment);
        log.debug("Utworzono nowy obiekt transakcji");

        model.addAttribute("paymentCategories", paymentCategoryService.findAll());
        model.addAttribute("budgets", budgetService.findAll());
        model.addAttribute("wallets", accountService.findAll());
        model.addAttribute("today", LocalDate.now());
        return "payments/addPayment";
    }

    @PostMapping(value = "/add")
    public String addPaymentToDataBase(@ModelAttribute("payment") Payment payment) {

        log.debug("Przekazana transakcja ma id: " + payment.getId());
        Payment savedPayment = paymentService.save(payment);
        log.debug("Wykonano 'save' na transakcji o ID: " + savedPayment.getId().toString());
        return "redirect:/payments/";
    }

    @GetMapping(value = "/{id}/edit")
    public String updatePayment(@PathVariable Long id, Model model) {
        Payment payment = paymentService.findByIdForAuthUser(id);
        model.addAttribute("payment", payment);

        model.addAttribute("paymentCategories", paymentCategoryService.findAll());
        model.addAttribute("budgets", budgetService.findAll());
        model.addAttribute("wallets", accountService.findAll());
        model.addAttribute("today", LocalDate.now());

        log.debug("Request update na transakcji o ID: " + id.toString());
        return "/payments/addPayment";
    }

    @GetMapping(value = "/{id}/delete")
    public String deletePayment(@PathVariable Long id) {
        log.debug("Request delete na transakcji o ID: " + id.toString());
        paymentService.deleteById(id);
        return "redirect:/payments/paymentList";
    }

    @GetMapping(value = "/exportcsv")
    public String exportCsvPayment() {
        log.debug("Request export csv with payments");
        try {
            csvService.createCsvReport();
        } catch (IOException e) {
            log.error("Exception during csv generating" + e);
        }
        return "redirect:/payments/paymentList";
    }


}
