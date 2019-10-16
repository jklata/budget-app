package pl.jklata.budgetapp.command;

import lombok.*;
import pl.jklata.budgetapp.domain.AccountType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountCommand {

    private Long id;
    private String name;
    private Currency currency;
    private BigDecimal initialBalance;
    private UserCommand user;
    private AccountType accountType;
    private List<PaymentCommand> payments = new ArrayList<>();
}
