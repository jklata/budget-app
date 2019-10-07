package pl.jklata.budgetapp.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;

    @Email
    private String email;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

}
