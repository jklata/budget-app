package pl.jklata.budgetapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private List<Transaction> transactions;

}
