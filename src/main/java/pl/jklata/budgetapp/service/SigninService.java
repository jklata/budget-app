package pl.jklata.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.domain.UserRole;
import pl.jklata.budgetapp.domain.enums.Role;
import pl.jklata.budgetapp.repository.UserRepository;
import pl.jklata.budgetapp.repository.UserRoleRepository;

import java.util.Collections;
import java.util.Optional;

/**
 * @author Jakub Klata, Pentacomp Systemy Informatyczne S.A.
 */
@Service
public class SigninService {

    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public SigninService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveNewUser(User user){
        user.setPassword(encryptPassword(user.getPassword()));
        Optional<UserRole> initialRole = userRoleRepository.findByRole(Role.USER);
        initialRole.ifPresent(userRole -> user.setUserRoles(Collections.singleton(userRole)));
        user.setPermissions("STANDARD");
        user.setActive(true);

        userRepository.save(user);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }


}
