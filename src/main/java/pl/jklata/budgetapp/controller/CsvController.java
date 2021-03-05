package pl.jklata.budgetapp.controller;

import java.io.ByteArrayInputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jklata.budgetapp.service.CsvService;

@Slf4j
@Controller
@RequestMapping("/csv")
@RequiredArgsConstructor
public class CsvController {

    private final CsvService csvService;

    @GetMapping()
    public String getView() {
        return "/csv";
    }

    @PostMapping("/export")
    public String postExportTask() {
        log.debug("Post export");
        return "redirect:/csv/";
    }

    @GetMapping(value = "/exportpdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdf() {
        ByteArrayInputStream inputStreamReturn = csvService.create();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=citiesreport.pdf");
        return ResponseEntity
            .ok()
            .headers(httpHeaders)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(inputStreamReturn));
    }

}
