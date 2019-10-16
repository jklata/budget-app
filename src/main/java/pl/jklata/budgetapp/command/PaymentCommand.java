package pl.jklata.budgetapp.command;

import lombok.*;
import pl.jklata.budgetapp.domain.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class PaymentCommand {

    private Long id;
    private LocalDate paymentDate;
    private LocalDate insertDate;
    private BigDecimal amount;
    private String title;
    private PaymentCategoryCommand paymentCategory;
    private PaymentType paymentType;
    private BudgetCommand budget;
    private Set<HashtagCommand> hashtags = new HashSet<>();
    private AccountCommand account;
}

