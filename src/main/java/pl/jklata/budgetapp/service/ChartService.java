package pl.jklata.budgetapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ChartService {

    private PaymentService paymentService;

    @Autowired
    public ChartService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    public Map<String, BigDecimal> getAllPaymentsOfGivenYearByMonthMap(int year) {

        Map<String, BigDecimal> paymentsByMonthThisYearMap = new LinkedHashMap<>();
        paymentsByMonthThisYearMap.put("Styczeń", getAllPaymentsOfGivenYearByMonth(year, 1));
        paymentsByMonthThisYearMap.put("Luty", getAllPaymentsOfGivenYearByMonth(year, 2));
        paymentsByMonthThisYearMap.put("Marzec", getAllPaymentsOfGivenYearByMonth(year, 3));
        paymentsByMonthThisYearMap.put("Kwiecień", getAllPaymentsOfGivenYearByMonth(year, 4));
        paymentsByMonthThisYearMap.put("Maj", getAllPaymentsOfGivenYearByMonth(year, 5));
        paymentsByMonthThisYearMap.put("Czerwiec", getAllPaymentsOfGivenYearByMonth(year, 6));
        paymentsByMonthThisYearMap.put("Lipiec", getAllPaymentsOfGivenYearByMonth(year, 7));
        paymentsByMonthThisYearMap.put("Sierpień", getAllPaymentsOfGivenYearByMonth(year, 8));
        paymentsByMonthThisYearMap.put("Wrzesień", getAllPaymentsOfGivenYearByMonth(year, 9));
        paymentsByMonthThisYearMap.put("Pazdziernik", getAllPaymentsOfGivenYearByMonth(year, 10));
        paymentsByMonthThisYearMap.put("Listopad", getAllPaymentsOfGivenYearByMonth(year, 11));
        paymentsByMonthThisYearMap.put("Grudzień", getAllPaymentsOfGivenYearByMonth(year, 12));

        BigDecimal sumForYear = paymentsByMonthThisYearMap.values().stream()
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        log.debug("Suma wydatków użytkownika: {}, {} roku: [{} zł]",paymentService.getAuthenticatedUser().getLogin() ,year, sumForYear);

        return paymentsByMonthThisYearMap;
    }

    private BigDecimal getAllPaymentsOfGivenYearByMonth(int year, int month) {
        return paymentService.findAllForAuthUser().stream()
                .filter(p -> p.getPaymentDate().isAfter(LocalDate.of(year, month, 1)))
                .filter(p -> p.getPaymentDate().isBefore(LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth())))
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
