package pl.jklata.budgetapp.domain;

import lombok.*;
import pl.jklata.budgetapp.domain.enums.Role;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "users")
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "userRoles") //fixme
    private Set<User> users;
}
