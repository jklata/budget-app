package pl.jklata.budgetapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.jklata.budgetapp.domain.UserRole;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class UserDto {

    private Long id;
    private String login;
    private String password;
    private String retypedPassword;
    private Set<UserRole> userRoles = new HashSet<>();
    private String permissions;
    private boolean active;

}
