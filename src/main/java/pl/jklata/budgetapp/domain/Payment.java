package pl.jklata.budgetapp.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.validator.annotations.DateNotInFutureValidation;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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

