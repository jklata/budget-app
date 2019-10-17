package pl.jklata.budgetapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.jklata.budgetapp.service.ChartService;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class ChartController {

    private ChartService chartService;

    @Autowired
    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("/reports")
    public String testChart(Model model){

        model.addAttribute("surveyMap", chartService.getAllPaymentsThisYearByMonthMap());

        return "reports-test";
    }
}
