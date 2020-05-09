package pl.jklata.budgetapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jklata.budgetapp.service.ChartService;
import pl.jklata.budgetapp.service.PaymentService;

import java.time.LocalDate;
import java.util.Objects;

@Controller
public class ChartController {

    private ChartService chartService;
    private PaymentService paymentService;

    @Autowired
    public ChartController(ChartService chartService, PaymentService paymentService) {
        this.chartService = chartService;
        this.paymentService = paymentService;
    }

    @GetMapping("/reports")
    public String getMainChart(Model model, @RequestParam(value = "year", required = false) Integer year) {


        model.addAttribute("listOfYears", paymentService.getDistinctYearFromAllPayments());

        if (Objects.isNull(year)) {
            year = LocalDate.now().getYear();
        }
        model.addAttribute("surveyMap", chartService.getAllPaymentsOfGivenYearByMonthMap(year));

        return "reports";
    }
}
