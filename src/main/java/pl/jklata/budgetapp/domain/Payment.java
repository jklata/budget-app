package pl.jklata.budgetapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import pl.jklata.budgetapp.domain.enums.PaymentType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"hashtags"})
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @Column(name = "insert_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public String getValuesForCsv() {
        String coma = ", ";
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(coma);
        sb.append(paymentDate);
        sb.append(coma);
        sb.append(amount);

        return sb.toString();
    }
}

