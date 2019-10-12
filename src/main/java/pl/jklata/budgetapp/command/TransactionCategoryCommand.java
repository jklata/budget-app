package pl.jklata.budgetapp.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TransactionCategoryCommand {

    private Long id;
    private String name;
    private String color;

}
