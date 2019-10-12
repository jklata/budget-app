package pl.jklata.budgetapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"hashtags"})
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;

    @Column(name = "insert_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate insertDate;

    private BigDecimal amount;

    private String title;

    @OneToOne
    private TransactionCategory transactionCategory;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @ManyToMany
    @JoinTable(name = "transaction_has_hashtag",
    joinColumns = @JoinColumn(name = "transaction_id"),
    inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private Set<Hashtag> hashtags;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}

