package pl.jklata.budgetapp.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BudgetCommand {

    private Long id;
    private String name;
    private BigDecimal budgetValue;
    private List<PaymentCommand> payments = new ArrayList<>();

}
