package pl.jklata.budgetapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jklata.budgetapp.converter.UserDtoToEntity;
import pl.jklata.budgetapp.converter.UserEntityToUserUpdateDto;
import pl.jklata.budgetapp.converter.UserUpdateDtoToEntity;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.domain.UserRole;
import pl.jklata.budgetapp.domain.enums.Role;
import pl.jklata.budgetapp.dto.UserDto;
import pl.jklata.budgetapp.dto.UserUpdateDto;
import pl.jklata.budgetapp.repository.UserRepository;
import pl.jklata.budgetapp.repository.UserRoleRepository;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private AuthUserService authUserService;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    private UserDtoToEntity userDtoToEntity;
    private UserEntityToUserUpdateDto userEntityToUserUpdateDto;
    private UserUpdateDtoToEntity userUpdateDtoToEntity;

    @Autowired
    public UserService(AuthUserService authUserService, UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, UserDtoToEntity userDtoToEntity, UserEntityToUserUpdateDto userEntityToUserUpdateDto, UserUpdateDtoToEntity userUpdateDtoToEntity) {
        this.authUserService = authUserService;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDtoToEntity = userDtoToEntity;
        this.userEntityToUserUpdateDto = userEntityToUserUpdateDto;
        this.userUpdateDtoToEntity = userUpdateDtoToEntity;
    }

    @Transactional
    public void signUpNewUser(UserDto userDto) {
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


    public void updateUser(UserUpdateDto userUpdateDto) {
        User userToSave = userUpdateDtoToEntity.convert(userUpdateDto);
        userToSave.setPassword(resolvePasswordToPersist(userUpdateDto));
        userRepository.save(userToSave);
    }

    private String resolvePasswordToPersist(UserUpdateDto userUpdateDto) {
        if (!Objects.isNull(userUpdateDto.getNewPassword())) {
            log.debug("Update User with newly typed password");
            return encryptPassword(userUpdateDto.getNewPassword());
        } else {
            log.debug("Update User without changing password");
            return userRepository.findByLogin(userUpdateDto.getLogin()).getPassword();
        }
    }

    public UserUpdateDto getAuthenticatedUserUpdateDto() {
        User authenticatedUser = authUserService.getAuthenticatedUser();
        return userEntityToUserUpdateDto.convert(authenticatedUser);
    }
}
