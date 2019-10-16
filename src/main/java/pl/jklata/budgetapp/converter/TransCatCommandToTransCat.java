package pl.jklata.budgetapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.command.PaymentCategoryCommand;
import pl.jklata.budgetapp.domain.PaymentCategory;

@Component
public class TransCatCommandToTransCat implements Converter<PaymentCategoryCommand, PaymentCategory> {

    @Override
    public PaymentCategory convert(PaymentCategoryCommand transactionCategory) {
        return null;
    }
}
