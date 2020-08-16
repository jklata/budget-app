package pl.jklata.budgetapp.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
@EqualsAndHashCode(exclude = "userRoles")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank()
    @Column(name = "user_login", unique = true)
    private String login;

    @NotBlank
    private String password;

    @Email
    private String email;

    @Lob
    private byte[] avatar;

    private String firstName;
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user") //fixme
    private List<Account> accounts;

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER) //fixme
    @JoinTable(name = "user_has_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> userRoles = new HashSet<>();

    @NotBlank
    private String permissions;

    @NotNull
    private boolean active;

    public List<String> getRoleList() {
    return userRoles.stream().map(u->u.getRole().toString()).collect(Collectors.toList());
    }

    public List<String> getPermissionList() {
        if (this.permissions.length() > 0) {
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                '}';
    }

}
