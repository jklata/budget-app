package pl.jklata.budgetapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.command.AccountCommand;
import pl.jklata.budgetapp.domain.Account;

@Component
public class AccountToAccountCommand implements Converter<Account, AccountCommand> {

    @Override
    public AccountCommand convert(Account source) {
        if(source == null){
            return null;
        }

        final AccountCommand accountCommand = new AccountCommand();
        accountCommand.setId(source.getId());
        accountCommand.setName(source.getName());
        accountCommand.setCurrency(source.getCurrency());
        //accountCommand.setUser();

        return null;
    }
}
