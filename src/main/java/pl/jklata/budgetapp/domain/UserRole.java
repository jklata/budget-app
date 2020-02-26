package pl.jklata.budgetapp.domain;

import lombok.Data;
import pl.jklata.budgetapp.domain.enums.Role;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "userRoles")
    private Set<User> users;
}
