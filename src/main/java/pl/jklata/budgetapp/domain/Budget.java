package pl.jklata.budgetapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "budget")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal budgetValue;

    @OneToMany(mappedBy = "budget")
    private List<Payment> payments;

}
