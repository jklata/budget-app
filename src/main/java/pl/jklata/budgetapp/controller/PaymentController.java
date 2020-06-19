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

    @GetMapping(value = "/payments")
    public String listPayments(
            ModelMap model, @SortDefault("id") Pageable pageable) {

        model.addAttribute("page", paymentService.findPaginated(pageable));

        return "payments/payments";
    }

    @GetMapping({"/{id}/show"})
    public String getPayment(@PathVariable Long id, Model model) {

        model.addAttribute("payment", paymentService.findByIdForAuthUser(id));
        return "payments/payment-show";
    }

    @GetMapping({"/addPayment"})
    public String getAddPayment(Model model) {

        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        model.addAttribute("payment", payment);
        log.debug("Utworzono nowy obiekt transakcji");

        model.addAttribute("paymentCategories", paymentCategoryService.findAll());
        model.addAttribute("budgets", budgetService.findAll());
        model.addAttribute("wallets", accountService.findAll());
        model.addAttribute("today", LocalDate.now());
        return "payments/add-payment";
    }

    //    todo zmienic nazwe endpointu
    @PostMapping({"addPaymentToList"})
    public String addPaymentToDataBase(@ModelAttribute Payment payment, @RequestParam String paymentCategoryEach, @RequestParam String walletEach, @RequestParam String budgetEach) {

        log.debug("Przekazana transakcja ma id: " + payment.getId());
        payment.setPaymentCategory(paymentCategoryService.findByName(paymentCategoryEach));
        payment.setAccount(accountService.findByName(walletEach));
        payment.setBudget(budgetService.findByName(budgetEach));
        Payment savedPayment = paymentService.save(payment);
        log.debug("Wykonano 'save' na transakcji o ID: " + savedPayment.getId().toString());
        return "redirect:/payments";
    }

    @GetMapping("/{id}/update")
    public String updatePayment(@PathVariable Long id, Model model) {
        model.addAttribute("payment", paymentService.findByIdForAuthUser(id));

//        model.addAttribute("transactionCategories", transactionCategoryService.findAll());
//        model.addAttribute("budgets", budgetService.findAll());
//        model.addAttribute("wallets", accountService.findAll());

        log.debug("Request update na transakcji o ID: " + id.toString());
        return "payments/add-payment";
    }

    @GetMapping("/{id}/delete")
    public String deletePayment(@PathVariable Long id) {
        log.debug("Request delete na transakcji o ID: " + id.toString());
        paymentService.deleteById(id);
        return "redirect:/payments";
    }

    @GetMapping("/exportcsv")
    public String exportCsvPayment() {
        log.debug("Request export csv with payments");
        try {
            csvService.createCsvReport();
        } catch (IOException e) {
            log.error("Exception during csv generating" + e);
        }
        return "redirect:/payments";
    }


}
