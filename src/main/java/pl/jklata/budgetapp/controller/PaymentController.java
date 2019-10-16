package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.service.AccountService;
import pl.jklata.budgetapp.service.BudgetService;
import pl.jklata.budgetapp.service.PaymentCategoryService;
import pl.jklata.budgetapp.service.PaymentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
public class PaymentController {


    private PaymentCategoryService paymentCategoryService;
    private PaymentService paymentService;
    private AccountService accountService;
    private BudgetService budgetService;

    @Autowired
    public PaymentController(PaymentCategoryService paymentCategoryService, PaymentService paymentService, AccountService accountService, BudgetService budgetService) {
        this.paymentCategoryService = paymentCategoryService;
        this.paymentService = paymentService;
        this.accountService = accountService;
        this.budgetService = budgetService;
    }


    @GetMapping({"/payments2"})
    public String getPayments(Model model) {

        model.addAttribute("payments", paymentService.findAll());
        return "payments/payments2";
    }

    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public String listPayments(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Payment> paymentPage = paymentService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("paymentPage", paymentPage);

        int totalPages = paymentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "payments/payments";
    }


    @GetMapping({"/{id}/show"})
    public String getPayment(@PathVariable Long id, Model model) {

        model.addAttribute("payment", paymentService.findById(id));
        return "payments/payment-show";
    }

    @GetMapping({"/addPayment"})
    public String getAddPayment(Model model) {

        model.addAttribute("payment", new Payment());
        log.debug("Utworzono nowy obiekt transakcji");

        model.addAttribute("paymentCategories", paymentCategoryService.findAll());
        model.addAttribute("budgets", budgetService.findAll());
        model.addAttribute("wallets", accountService.findAll());
        return "payments/add-payment";
    }

    @GetMapping("/{id}/update")
    public String updatePayment(@PathVariable Long id, Model model) {
        model.addAttribute("payment", paymentService.findById(id));

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


}