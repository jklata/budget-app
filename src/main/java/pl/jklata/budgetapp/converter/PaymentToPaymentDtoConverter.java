package pl.jklata.budgetapp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.dto.PaymentDto;

@Component
public class PaymentToPaymentDtoConverter implements Converter<Payment, PaymentDto> {

    private ModelMapper modelMapper;

    @Autowired
    public PaymentToPaymentDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentDto convert(Payment source) {
        if (source == null) {
            return null;
        }

        PaymentDto paymentDto = modelMapper.map(source, PaymentDto.class);
        if (paymentDto.getPaymentType() == PaymentType.EXPENSE) {
            paymentDto.setAmount(paymentDto.getAmount().abs());
        }

        return paymentDto;
    }
}
