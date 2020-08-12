package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.dto.PaymentDto;
import pl.jklata.budgetapp.service.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPaymentDate(LocalDate.now());
        paymentDto.setIdForUser(paymentService.resolveNextIdForUser());
        paymentDto.setPaymentType(PaymentType.EXPENSE);
        model.addAttribute("payment", paymentDto);
        log.debug("New PaymentDTO created");

        model.addAttribute("paymentCategories", paymentCategoryService.findAll());
        model.addAttribute("budgets", budgetService.findAll());
        model.addAttribute("wallets", accountService.findAll());
        model.addAttribute("today", LocalDate.now());
        return "payments/addPayment";
    }

    @PostMapping(value = "/add")
    public String addPaymentToDataBase(@Valid @ModelAttribute("payment") PaymentDto paymentDto, BindingResult binding, Model model) {

        log.debug("Received payment id: {} ", paymentDto.getId());
        if (binding.hasErrors()) {
            for (ObjectError error : binding.getAllErrors()) {
                log.info("Validation errors during saving payment. Error: [object = {}, message = {}]",
                        error.getObjectName(), error.getDefaultMessage());
            }
            model.addAttribute(paymentDto);
            //todo: przekazać do widoku atrybuty: paymentCategory, budget, account
            return "payments/addPayment";
        }
        Payment savedPayment = paymentService.save(paymentDto);
        log.debug("Payment has been saved. Payment id: {}", savedPayment.getId());
        return "redirect:/payments/";
    }

    @GetMapping(value = "/{id}/edit")
    public String updatePayment(@PathVariable Long id, Model model, HttpSession session) {
        PaymentDto paymentDto = paymentService.findByIdForAuthUser(id);
        model.addAttribute("payment", paymentDto);

        model.addAttribute("paymentCategories", paymentCategoryService.findAll());
        model.addAttribute("budgets", budgetService.findAll());
        model.addAttribute("wallets", accountService.findAll());
        model.addAttribute("today", LocalDate.now());
        session.setAttribute("beforeType", paymentDto.getPaymentType());

        log.debug("Request update na transakcji o ID: " + id.toString());
        return "/payments/addPayment";
    }

    @GetMapping(value = "/{id}/delete")
    public String deletePayment(@PathVariable Long id) {
        log.debug("Request delete na transakcji o ID: " + id.toString());
        paymentService.deleteById(id);
        return "redirect:/payments/";
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
