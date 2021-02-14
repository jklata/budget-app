package pl.jklata.budgetapp.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.dto.PaymentDto;

class PaymentToPaymentDtoConverterTest {

    private PaymentToPaymentDtoConverter paymentToPaymentDtoConverter;

    @BeforeEach
    void setup() {
        ModelMapper modelMapper = new ModelMapper();
        paymentToPaymentDtoConverter = new PaymentToPaymentDtoConverter(modelMapper);
    }

    @Test
    void should_return_absolute_amount_for_expense() {
        Payment payment = Payment.builder()
            .id(1L)
            .paymentType(PaymentType.EXPENSE)
            .amount(new BigDecimal(200).negate())
            .build();

        PaymentDto expected = PaymentDto.builder()
            .id(1L)
            .paymentType(PaymentType.EXPENSE)
            .amount(new BigDecimal(200))
            .build();

        PaymentDto actual = paymentToPaymentDtoConverter.convert(payment);

        assertTrue(Objects.nonNull(actual));
        assertEquals(expected, actual);

    }

    @Test
    void should_return_absolute_amount_for_income() {
        Payment payment = Payment.builder()
            .id(1L)
            .paymentType(PaymentType.INCOME)
            .amount(new BigDecimal(200))
            .build();

        PaymentDto expected = PaymentDto.builder()
            .id(1L)
            .paymentType(PaymentType.INCOME)
            .amount(new BigDecimal(200))
            .build();

        PaymentDto actual = paymentToPaymentDtoConverter.convert(payment);

        assertTrue(Objects.nonNull(actual));
        assertEquals(expected, actual);

    }

}
