package pl.jklata.budgetapp.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class HashtagCommand {

    private Long id;
    private String name;
    private List<TransactionCommand> transactions = new ArrayList<>();

}
