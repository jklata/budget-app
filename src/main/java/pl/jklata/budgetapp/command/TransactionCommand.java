package pl.jklata.budgetapp.command;

import lombok.*;
import pl.jklata.budgetapp.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class TransactionCommand {

    private Long id;
    private LocalDate transactionDate;
    private LocalDate insertDate;
    private BigDecimal amount;
    private String title;
    private TransactionCategoryCommand transactionCategory;
    private TransactionType transactionType;
    private BudgetCommand budget;
    private Set<HashtagCommand> hashtags = new HashSet<>();
    private AccountCommand account;
}

