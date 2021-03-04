package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/csv")
public class CsvController {

    @GetMapping()
    public String getView() {
        return "/csv";
    }

    @PostMapping("/export")
    public String postExportTask() {
        log.debug("Post export");
        return "redirect:/csv/";
    }

}
