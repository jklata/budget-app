package pl.jklata.budgetapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.command.PaymentCategoryCommand;
import pl.jklata.budgetapp.domain.PaymentCategory;

@Component
public class TransCatToTransCatCommand implements Converter<PaymentCategory, PaymentCategoryCommand> {

    @Override
    public PaymentCategoryCommand convert(PaymentCategory paymentCategory) {
        return null;
    }
}
