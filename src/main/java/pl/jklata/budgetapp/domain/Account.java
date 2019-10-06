package pl.jklata.budgetapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    BigDecimal initialBalance;

    @ManyToOne
    User user;

    @Enumerated(value = EnumType.STRING)
    AccountType accountType;

    @OneToMany(mappedBy = "account")
    List<Transaction> transactions;
}
