package pl.jklata.budgetapp.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.dto.PaymentDto;

@Component
@RequiredArgsConstructor
public class PaymentToPaymentDtoConverter implements Converter<Payment, PaymentDto> {

    private final ModelMapper modelMapper;

    @Override
    public PaymentDto convert(Payment source) {
        PaymentDto paymentDto = modelMapper.map(source, PaymentDto.class);
        if (paymentDto.getPaymentType() == PaymentType.EXPENSE) {
            paymentDto.setAmount(paymentDto.getAmount().abs());
        }

        return paymentDto;
    }
}
