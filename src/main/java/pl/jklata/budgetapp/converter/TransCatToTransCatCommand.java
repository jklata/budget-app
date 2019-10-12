package pl.jklata.budgetapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.command.TransactionCategoryCommand;
import pl.jklata.budgetapp.domain.TransactionCategory;

@Component
public class TransCatToTransCatCommand implements Converter<TransactionCategory, TransactionCategoryCommand> {

    @Override
    public TransactionCategoryCommand convert(TransactionCategory transactionCategory) {
        return null;
    }
}
