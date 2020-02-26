package pl.jklata.budgetapp.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_login")
    private String login;
    private String password;

    @Email
    private String email;

    @Lob
    private Byte[] avatar;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    @ManyToMany
    @JoinTable(name = "user_has_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> userRoles = new HashSet<>();

}
