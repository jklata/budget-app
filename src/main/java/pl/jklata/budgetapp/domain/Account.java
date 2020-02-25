package pl.jklata.budgetapp.domain;

import lombok.*;
import pl.jklata.budgetapp.domain.enums.AccountType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"transactions"})
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Currency currency;
    private BigDecimal initialBalance;

    @ManyToOne
    private User user;

    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

    @OneToMany(mappedBy = "account")
    private List<Payment> payments = new ArrayList<>();
}
