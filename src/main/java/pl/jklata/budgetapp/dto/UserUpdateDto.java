package pl.jklata.budgetapp.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import pl.jklata.budgetapp.domain.UserRole;
import pl.jklata.budgetapp.validator.annotations.MatchUpdatePasswordsValidation;
import pl.jklata.budgetapp.validator.annotations.OldPasswordValidation;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Slf4j
@MatchUpdatePasswordsValidation(newPassword = "newPassword", retypedPassword = "retypedPassword",
    changePassword = "changePassword", message = "{validator.password.notmatch}")
@OldPasswordValidation(login = "login", oldPassword = "oldPassword", changePassword = "changePassword", message = "{validator.password.old.wrong}")
public class UserUpdateDto {

    @NotNull
    private Long id;

    @NotNull
    private String login;

    private MultipartFile avatar;

    @Size(min = 6, message = "{validator.password.length}")
    private String oldPassword;

    @Size(min = 6, message = "{validator.password.length}")
    private String newPassword;

    @Size(min = 6, message = "{validator.password.length}")
    private String retypedPassword;

    private String password;
    private Set<UserRole> userRoles = new HashSet<>();
    private String permissions;
    private boolean active;
    private boolean changePassword;

    @NotBlank(message = "{validator.invalid.null}")
    private String firstName;
    @NotBlank(message = "{validator.invalid.null}")
    private String lastName;

    @NotBlank(message = "{validator.invalid.null}")
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "{validator.email.format}")
    private String email;


}
