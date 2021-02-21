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
public class PaymentDtoToPaymentConverter implements Converter<PaymentDto, Payment> {

    private final ModelMapper modelMapper;

    @Override
    public Payment convert(PaymentDto source) {
        Payment payment = modelMapper.map(source, Payment.class);
        if (payment.getPaymentType() == PaymentType.EXPENSE && (payment.getAmount().signum() == 1)) {
            payment.setAmount(payment.getAmount().negate());
        }
        return payment;
    }
}
