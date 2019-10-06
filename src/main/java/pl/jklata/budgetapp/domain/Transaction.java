package pl.jklata.budgetapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "transaction_date")
    LocalDate transactionDate;
    @Column(name = "insert_date")
    LocalDate insertDate;
    @Column(name = "amount")
    BigDecimal amount;
    @Column(name = "payee")
    String payee;

    @OneToOne
    TransactionCategory transactionCategory;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_type")
    TransactionType transactionType;

    @ManyToOne
    Budget budget;

    @ManyToMany
    Set<Hashtag> hashtags;

    @ManyToOne
    Account account;
}

