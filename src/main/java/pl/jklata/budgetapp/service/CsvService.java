package pl.jklata.budgetapp.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.enums.PaymentHeaders;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class CsvService {

    private PaymentService paymentService;

    @Autowired
    public CsvService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void createCsvReport() throws IOException {
        List<Payment> payments = paymentService.findAll();

        FileWriter out = new FileWriter(fileNameBuilder("csv"));
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(PaymentHeaders.class))) {
            for (Payment payment : payments) {
                printer.printRecord(payment.getValuesForCsv());
            }
        }
    }

    private Class<? extends Enum<?>> resolveHeadersForCsv() {
        return null;
    }

    private String fileNameBuilder(String fileFormat) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd_HHmmss"));
        return String.format("Wyciag_platnosci_%S.%S", timestamp, fileFormat);
    }
}
