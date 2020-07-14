package pl.jklata.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.converter.UserDtoToEntity;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.domain.UserRole;
import pl.jklata.budgetapp.domain.enums.Role;
import pl.jklata.budgetapp.dto.UserDto;
import pl.jklata.budgetapp.repository.UserRepository;
import pl.jklata.budgetapp.repository.UserRoleRepository;

import java.util.Collections;
import java.util.Optional;

/**
 * @author Jakub Klata, Pentacomp Systemy Informatyczne S.A.
 */
@Service
public class SignupService {

    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    PasswordEncoder passwordEncoder;
    UserDtoToEntity userDtoToEntity;

    @Autowired
    public SignupService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, UserDtoToEntity userDtoToEntity) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDtoToEntity = userDtoToEntity;
    }

    public void saveNewUser(UserDto userDto) {
        userDto.setPassword(encryptPassword(userDto.getPassword()));
        Optional<UserRole> initialRole = userRoleRepository.findByRole(Role.USER);
        initialRole.ifPresent(userRole -> userDto.setUserRoles(Collections.singleton(userRole)));
        userDto.setPermissions("STANDARD");
        userDto.setActive(true);

        User userToPersist = userDtoToEntity.convert(userDto);
        if (userToPersist != null) {
            userRepository.save(userToPersist);
        }
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }


}
