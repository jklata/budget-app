package pl.jklata.budgetapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.validator.annotations.DateNotInFutureValidation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"hashtags"})
@ToString(exclude = {"budget", "account", "hashtags"})
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idForUser;

    @ManyToOne
    @NotNull
    private User user;

    @Column(name = "transaction_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @Column(name = "insert_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @DateNotInFutureValidation(message = "{validator.invalid.null}")
    private LocalDate insertDate;

    private BigDecimal amount;

    private String title;

    @OneToOne
    private PaymentCategory paymentCategory;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @ManyToMany
    @JoinTable(name = "payment_has_hashtag",
            joinColumns = @JoinColumn(name = "payment_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private Set<Hashtag> hashtags;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}

