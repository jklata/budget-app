package pl.jklata.budgetapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.jklata.budgetapp.domain.UserRole;
import pl.jklata.budgetapp.validator.annotations.MatchPasswordsValidation;
import pl.jklata.budgetapp.validator.annotations.UniqueLoginValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
@MatchPasswordsValidation(password = "password", retypedPassword = "retypedPassword", message = "{validator.password.notmatch}")
public class UserDto {

    private Long id;

    @NotBlank(message = "{validator.invalid.null}")
    @UniqueLoginValidation(message = "{validator.unique.login}")
    private String login;

    @NotBlank(message = "{validator.invalid.null}")
    @Size(min = 5, message = "{validator.password.length}")
    private String password;

    @NotBlank(message = "{validator.invalid.null}")
    @Size(min = 5, message = "{validator.password.length}")
    private String retypedPassword;

    private Set<UserRole> userRoles = new HashSet<>();
    private String permissions;
    private boolean active;

}
