package pl.jklata.budgetapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.command.TransactionCategoryCommand;
import pl.jklata.budgetapp.domain.TransactionCategory;

@Component
public class TransCatCommandToTransCat implements Converter<TransactionCategoryCommand, TransactionCategory> {

    @Override
    public TransactionCategory convert(TransactionCategoryCommand transactionCategory) {
        return null;
    }
}
