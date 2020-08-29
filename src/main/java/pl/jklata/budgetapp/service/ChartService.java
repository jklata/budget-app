package pl.jklata.budgetapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.dto.PaymentDto;
import pl.jklata.budgetapp.dto.converter.PaymentEntityToDto;
import pl.jklata.budgetapp.repository.PaymentRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChartService {

    private PaymentRepository paymentRepository;
    private AuthUserService authUserService;
    private PaymentEntityToDto paymentEntityToDto;

    @Autowired
    public ChartService(PaymentRepository paymentRepository, AuthUserService authUserService, PaymentEntityToDto paymentEntityToDto) {
        this.paymentRepository = paymentRepository;
        this.authUserService = authUserService;
        this.paymentEntityToDto = paymentEntityToDto;
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

        log.debug("Report requested data for payments of [user = {}; year = {}].", authUserService.getAuthenticatedUser().getLogin(), year);

        return paymentsByMonthThisYearMap;
    }

    private BigDecimal getAllPaymentsOfGivenYearByMonth(int year, int month) {
        return findAllForAuthUserAndYearAndMonth(year, month).stream()
                .map(PaymentDto::getAmountWithSign)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<PaymentDto> findAllForAuthUserAndYearAndMonth(int year, int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, startDate.lengthOfMonth());

        return paymentRepository.findAllByUserAndPaymentDateBetween(authUserService.getAuthenticatedUser(), startDate, endDate).stream()
                .map(payment -> paymentEntityToDto.convert(payment))
                .collect(Collectors.toList());
    }


    public List<Integer> getDistinctYearFromAllPayments() {
        return paymentRepository.getDistinctYearFromAllPayments(authUserService.getAuthenticatedUser());
    }

}
