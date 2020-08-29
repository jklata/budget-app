package pl.jklata.budgetapp.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.dto.PaymentDto;

@Component
public class PaymentDtoToEntity implements Converter<PaymentDto, Payment> {

    private ModelMapper modelMapper;

    @Autowired
    public PaymentDtoToEntity(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Payment convert(PaymentDto source) {
        if (source == null) {
            return null;
        }
        Payment payment = modelMapper.map(source, Payment.class);
        if (payment.getPaymentType() == PaymentType.EXPENSE && (payment.getAmount().signum() == 1)) {
            payment.setAmount(payment.getAmount().negate());
        }
        return payment;
    }
}
