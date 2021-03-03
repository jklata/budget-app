package pl.jklata.budgetapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.jklata.budgetapp.converter.PaymentToPaymentDtoConverter;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
class ChartServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private AuthUserService authUserService;

    private ChartService chartService;

    private static final int YEAR = 2020;

    @BeforeEach
    void setup() {
        PaymentToPaymentDtoConverter paymentToPaymentDtoConverter = new PaymentToPaymentDtoConverter(
            new ModelMapper());
        chartService = new ChartService(paymentRepository, authUserService,
            paymentToPaymentDtoConverter);
    }

    @Test
    void shouldMapPaymentsByYear() {

        Map<String, BigDecimal> expected = new LinkedHashMap<>();
        expected.put("Styczeń", BigDecimal.valueOf(100));
        expected.put("Luty", BigDecimal.valueOf(200));
        expected.put("Marzec", BigDecimal.valueOf(-300));
        expected.put("Kwiecień", BigDecimal.valueOf(400));
        expected.put("Maj", BigDecimal.valueOf(-500));
        expected.put("Czerwiec", BigDecimal.valueOf(-600));
        expected.put("Lipiec", BigDecimal.valueOf(700));
        expected.put("Sierpień", BigDecimal.valueOf(800));
        expected.put("Wrzesień", BigDecimal.valueOf(-900));
        expected.put("Październik", BigDecimal.valueOf(1000));
        expected.put("Listopad", BigDecimal.valueOf(1100));
        expected.put("Grudzień", BigDecimal.valueOf(0));

        when(authUserService.getAuthenticatedUser()).thenReturn(User.builder().id(1L).build());

        // mocking repository query for payments by each month of given 2020 year
        // each month has two different payments which corresponding to expected values
        when(paymentRepository.findAllByUserAndPaymentDateBetween(any(), getStartDate(1),
            getEndDate(1))).thenReturn(
            Arrays.asList(getPayment(50, 1), getPayment(50, 1)));

        when(paymentRepository.findAllByUserAndPaymentDateBetween(any(), getStartDate(2),
            getEndDate(2))).thenReturn(
            Arrays.asList(getPayment(50, 2), getPayment(150, 2)));

        when(paymentRepository.findAllByUserAndPaymentDateBetween(any(), getStartDate(3),
            getEndDate(3))).thenReturn(
            Arrays.asList(getPayment(-400, 3), getPayment(100, 3)));

        when(paymentRepository.findAllByUserAndPaymentDateBetween(any(), getStartDate(4),
            getEndDate(4))).thenReturn(
            Arrays.asList(getPayment(50, 4), getPayment(350, 4)));

        when(paymentRepository.findAllByUserAndPaymentDateBetween(any(), getStartDate(5),
            getEndDate(5))).thenReturn(
            Arrays.asList(getPayment(-500, 5), getPayment(0, 5)));

        when(paymentRepository.findAllByUserAndPaymentDateBetween(any(), getStartDate(6),
            getEndDate(6))).thenReturn(
            Arrays.asList(getPayment(-500, 6), getPayment(-100, 6)));

        when(paymentRepository.findAllByUserAndPaymentDateBetween(any(), getStartDate(7),
            getEndDate(7))).thenReturn(
            Arrays.asList(getPayment(1000, 7), getPayment(-300, 7)));

        when(paymentRepository.findAllByUserAndPaymentDateBetween(any(), getStartDate(8),
            getEndDate(8))).thenReturn(
            Arrays.asList(getPayment(820, 8), getPayment(-20, 8)));

        when(paymentRepository.findAllByUserAndPaymentDateBetween(any(), getStartDate(9),
            getEndDate(9))).thenReturn(
            Arrays.asList(getPayment(-450, 9), getPayment(-450, 9)));

        when(paymentRepository.findAllByUserAndPaymentDateBetween(any(), getStartDate(10),
            getEndDate(10))).thenReturn(
            Arrays.asList(getPayment(1000, 10), getPayment(0, 10)));

        when(paymentRepository.findAllByUserAndPaymentDateBetween(any(), getStartDate(11),
            getEndDate(11))).thenReturn(
            Arrays.asList(getPayment(1000, 11), getPayment(100, 11)));

        when(paymentRepository.findAllByUserAndPaymentDateBetween(any(), getStartDate(12),
            getEndDate(12))).thenReturn(
            Arrays.asList(getPayment(400, 12), getPayment(-400, 12)));

        Map<String, BigDecimal> allPaymentsOfGivenYearByMonthMap = chartService
            .getAllPaymentsOfGivenYearByMonthMap(YEAR);

        assertEquals(expected, allPaymentsOfGivenYearByMonthMap);


    }


    private Payment getPayment(int amount, int month) {
        return Payment.builder()
            .paymentType(amount > 0 ? PaymentType.INCOME : PaymentType.EXPENSE)
            .amount(BigDecimal.valueOf(amount))
            .paymentDate(LocalDate.of(YEAR, month, 15))
            .build();
    }

    private LocalDate getRawStartDate(int month) {
        return LocalDate.of(YEAR, month, 1);
    }

    private LocalDate getStartDate(int month) {
        return eq(LocalDate.of(YEAR, month, 1));
    }

    private LocalDate getEndDate(int month) {
        return eq(LocalDate.of(YEAR, month, getRawStartDate(month).lengthOfMonth()));
    }

}
