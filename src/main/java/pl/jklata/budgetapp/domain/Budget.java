package pl.jklata.budgetapp.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "budget")
@ToString(exclude = "payments")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal budgetValue;

    @OneToMany(mappedBy = "budget")
    private List<Payment> payments;

}
