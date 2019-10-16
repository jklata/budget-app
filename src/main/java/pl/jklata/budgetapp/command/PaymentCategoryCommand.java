package pl.jklata.budgetapp.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PaymentCategoryCommand {

    private Long id;
    private String name;
    private String color;

}
