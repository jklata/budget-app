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

        FileWriter out = new FileWriter(fileNameCreator());
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(PaymentHeaders.class))) {
            for (Payment payment : payments) {
                printer.printRecord(payment.getValuesForCsv());
            }
        }
    }

    private Class<? extends Enum<?>> resolveHeadersForCsv() {
        return null;
    }

    //todo zaimplementować generowanie nazwy z timestampem i ew. numerem strony
    private String fileNameCreator() {
        return "test_name.csv";
    }
}
