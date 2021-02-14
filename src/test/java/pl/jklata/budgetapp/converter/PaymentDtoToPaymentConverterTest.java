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

class PaymentDtoToPaymentConverterTest {

    private PaymentDtoToPaymentConverter paymentDtoToPaymentConverter;

    @BeforeEach
    void setup() {
        ModelMapper modelMapper = new ModelMapper();
        paymentDtoToPaymentConverter = new PaymentDtoToPaymentConverter(modelMapper);
    }

    @Test
    void should_convert_expense_with_negative_amount() {
        PaymentDto paymentDto = PaymentDto.builder()
            .id(1L)
            .paymentType(PaymentType.EXPENSE)
            .amount(new BigDecimal(200))
            .build();

        Payment expected = Payment.builder()
            .id(1L)
            .paymentType(PaymentType.EXPENSE)
            .amount(new BigDecimal(200).negate())
            .build();

        Payment actual = paymentDtoToPaymentConverter.convert(paymentDto);

        assertTrue(Objects.nonNull(actual));
        assertEquals(expected, actual);
    }

    @Test
    void should_convert_income_with_positive_amount() {
        PaymentDto paymentDto = PaymentDto.builder()
            .id(1L)
            .paymentType(PaymentType.INCOME)
            .amount(new BigDecimal(200))
            .build();

        Payment expected = Payment.builder()
            .id(1L)
            .paymentType(PaymentType.INCOME)
            .amount(new BigDecimal(200))
            .build();

        Payment actual = paymentDtoToPaymentConverter.convert(paymentDto);

        assertTrue(Objects.nonNull(actual));
        assertEquals(expected, actual);
    }


    @Test
    void should_return_null() {
        PaymentDto paymentDto = null;

        Payment actual = paymentDtoToPaymentConverter.convert(paymentDto);

        assertTrue(Objects.isNull(actual));
    }
}
