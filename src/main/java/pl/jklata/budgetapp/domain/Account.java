package pl.jklata.budgetapp.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.jklata.budgetapp.domain.enums.AccountType;

@Data
@EqualsAndHashCode(exclude = "payments")
@ToString(exclude = "payments")
@Entity
@Table(name = "account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
