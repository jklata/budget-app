package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jklata.budgetapp.service.ChartService;
import pl.jklata.budgetapp.service.PaymentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
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
        int currentYear = LocalDate.now().getYear();

        long startTime1 = System.nanoTime();
        List<Integer> listOfYears = paymentService.getDistinctYearFromAllPayments();
        long endTime1 = System.nanoTime();
        log.debug("Time of getDistinctYearFromAllPayments: {}ms", (endTime1 - startTime1) / 1000000);

        if (listOfYears.isEmpty()) {
            listOfYears.add(currentYear);
        }

        model.addAttribute("listOfYears", listOfYears);

        if (Objects.isNull(year)) {
            year = currentYear;
        }
        startTime1 = System.nanoTime();
        model.addAttribute("surveyMap", chartService.getAllPaymentsOfGivenYearByMonthMap(year));
        endTime1 = System.nanoTime();
        log.debug("Time of getAllPaymentsOfGivenYearByMonthMap: {}ms", (endTime1 - startTime1) / 1000000);

        return "reports";
    }
}
