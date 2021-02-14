package pl.jklata.budgetapp.service;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jklata.budgetapp.converter.UserDtoToUserConverter;
import pl.jklata.budgetapp.converter.UserToUserUpdateDtoConverter;
import pl.jklata.budgetapp.converter.UserUpdateDtoToUserConverter;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.domain.UserRole;
import pl.jklata.budgetapp.domain.enums.Role;
import pl.jklata.budgetapp.dto.UserDto;
import pl.jklata.budgetapp.dto.UserUpdateDto;
import pl.jklata.budgetapp.repository.UserRepository;
import pl.jklata.budgetapp.repository.UserRoleRepository;

@Slf4j
@Service
public class UserService {

    private AuthUserService authUserService;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    private UserDtoToUserConverter userDtoToUserConverter;
    private UserToUserUpdateDtoConverter userToUserUpdateDtoConverter;
    private UserUpdateDtoToUserConverter userUpdateDtoToUserConverter;

    @Autowired
    public UserService(AuthUserService authUserService, UserRepository userRepository,
        UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder,
        UserDtoToUserConverter userDtoToUserConverter,
        UserToUserUpdateDtoConverter userToUserUpdateDtoConverter,
        UserUpdateDtoToUserConverter userUpdateDtoToUserConverter) {
        this.authUserService = authUserService;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.userToUserUpdateDtoConverter = userToUserUpdateDtoConverter;
        this.userUpdateDtoToUserConverter = userUpdateDtoToUserConverter;
    }

    @Transactional
    public User signUpNewUser(UserDto userDto) {
        userDto.setPassword(encryptPassword(userDto.getPassword()));
        Optional<UserRole> initialRole = userRoleRepository.findByRole(Role.USER);
        initialRole.ifPresent(userRole -> userDto.setUserRoles(Collections.singleton(userRole)));
        userDto.setPermissions("STANDARD");
        userDto.setActive(true);

        User userToPersist = userDtoToUserConverter.convert(userDto);
        if (userToPersist != null) {
            userRepository.save(userToPersist);
        }
        return userToPersist;
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }


    public User updateUser(UserUpdateDto userUpdateDto) {
        User userToSave = userUpdateDtoToUserConverter.convert(userUpdateDto);
        userToSave.setPassword(resolvePasswordToPersist(userUpdateDto));
        userRepository.save(userToSave);
        return userToSave;
    }

    private String resolvePasswordToPersist(UserUpdateDto userUpdateDto) {
        if (Objects.nonNull(userUpdateDto.getNewPassword())) {
            log.debug("Update User with newly typed password");
            return encryptPassword(userUpdateDto.getNewPassword());
        } else {
            log.debug("Update User without changing password");
            return userRepository.findByLogin(userUpdateDto.getLogin()).getPassword();
        }
    }

    public UserUpdateDto getAuthenticatedUserUpdateDto() {
        User authenticatedUser = authUserService.getAuthenticatedUser();
        return userToUserUpdateDtoConverter.convert(authenticatedUser);
    }
}
