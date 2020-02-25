package pl.jklata.budgetapp.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "payment_category")
public class PaymentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;

}
