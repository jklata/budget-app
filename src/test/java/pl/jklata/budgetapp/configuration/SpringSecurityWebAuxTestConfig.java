package pl.jklata.budgetapp.configuration;

import java.util.Arrays;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.domain.UserPrincipal;
import pl.jklata.budgetapp.domain.UserRole;
import pl.jklata.budgetapp.domain.enums.Role;

@AllArgsConstructor
@Slf4j
@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {

        UserRole standardUserRole = UserRole.builder()
            .role(Role.USER)
            .build();
        UserRole adminRole = UserRole.builder()
            .role(Role.ADMIN)
            .build();

        User user1 = User.builder()
            .login("user")
            .password(passwordEncoder.encode("user123"))
            .email("test@gmail.com")
            .firstName("Jan")
            .lastName("Kowalski")
            .userRoles(Collections.singleton(standardUserRole))
            .permissions("STANDARD")
            .active(true)
            .build();

        User admin = User.builder()
            .login("admin")
            .password(passwordEncoder.encode("admin123"))
            .email("test2@gmail.com")
            .firstName("Admin")
            .lastName("Admiński")
            .userRoles(Collections.singleton(adminRole))
            .permissions("ALL")
            .active(true)
            .build();

        return new InMemoryUserDetailsManager(
            Arrays.asList(new UserPrincipal(user1), new UserPrincipal(admin)));
    }
}
