package pl.jklata.budgetapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
public class ChartService {

    private PaymentService paymentService;

    @Autowired
    public ChartService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    public BigDecimal getAllPaymentsThisYearByMonth (int month){
        return paymentService.findAllForAuthUser().stream()
                .filter(p->p.getPaymentDate().isAfter(LocalDate.of(LocalDate.now().getYear(), month,1)))
                .filter(p->p.getPaymentDate().isBefore(LocalDate.of(LocalDate.now().getYear(), month,LocalDate.of(LocalDate.now().getYear(), month, 10).lengthOfMonth())))
                .map(p->p.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<String, BigDecimal> getAllPaymentsThisYearByMonthMap(){

        Map<String, BigDecimal> paymentsByMonthThisYearMap = new LinkedHashMap<>();
        paymentsByMonthThisYearMap.put("Styczeń", getAllPaymentsThisYearByMonth(1));
        paymentsByMonthThisYearMap.put("Luty", getAllPaymentsThisYearByMonth(2));
        paymentsByMonthThisYearMap.put("Marzec", getAllPaymentsThisYearByMonth(3));
        paymentsByMonthThisYearMap.put("Kwiecień", getAllPaymentsThisYearByMonth(4));
        paymentsByMonthThisYearMap.put("Maj", getAllPaymentsThisYearByMonth(5));
        paymentsByMonthThisYearMap.put("Czerwiec", getAllPaymentsThisYearByMonth(6));
        paymentsByMonthThisYearMap.put("Lipiec", getAllPaymentsThisYearByMonth(7));
        paymentsByMonthThisYearMap.put("Sierpień", getAllPaymentsThisYearByMonth(8));
        paymentsByMonthThisYearMap.put("Wrzesień", getAllPaymentsThisYearByMonth(9));
        paymentsByMonthThisYearMap.put("Pazdziernik", getAllPaymentsThisYearByMonth(10));
        paymentsByMonthThisYearMap.put("Listopad", getAllPaymentsThisYearByMonth(11));
        paymentsByMonthThisYearMap.put("Grudzień", getAllPaymentsThisYearByMonth(12));

        log.debug("Wszystkie wydatki tego roku: " + paymentsByMonthThisYearMap.toString());

        return paymentsByMonthThisYearMap;
    }
}
