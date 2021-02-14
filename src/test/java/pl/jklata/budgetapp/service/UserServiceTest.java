package pl.jklata.budgetapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    private UserService userService;
    @Mock
    private AuthUserService authUserService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
        UserToUserUpdateDtoConverter userToUserUpdateDtoConverter = new UserToUserUpdateDtoConverter();
        UserUpdateDtoToUserConverter userUpdateDtoToUserConverter = new UserUpdateDtoToUserConverter();
        userService = new UserService(authUserService, userRepository, userRoleRepository,
            passwordEncoder,
            userDtoToUserConverter, userToUserUpdateDtoConverter, userUpdateDtoToUserConverter);

    }

    @Test
    void should_sign_up_new_user() {
        //given
        UserDto userDto = UserDto.builder()
            .id(1L)
            .login("login")
            .active(false)
            .password("notEncoded")
            .permissions(null)
            .userRoles(null)
            .build();

        User expected = User.builder()
            .id(1L)
            .login("login")
            .active(true)
            .password("encodedPass")
            .permissions("STANDARD")
            .userRoles(Collections.singleton(UserRole.builder().role(Role.USER).build()))
            .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPass");
        when(userRoleRepository.findByRole(any())).thenReturn(
            Optional.of(UserRole.builder().role(Role.USER).build()));
        //when
        User actual = userService.signUpNewUser(userDto);

        //then
        verify(userRepository, Mockito.times(1)).save(any());
        assertTrue(Objects.nonNull(actual));
        assertEquals(expected, actual);
    }

    @Test
    void should_update_new_password() {

        //given
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .id(1L)
            .login("login")
            .oldPassword("old")
            .newPassword("new")
            .retypedPassword("new")
            .password("pass")
            .changePassword(true)
            .build();

        User expected = User.builder()
            .id(1L)
            .login("login")
            .password("encodedPass")
            .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPass");
        //when
        User actual = userService.updateUser(userUpdateDto);

        //then
        verify(passwordEncoder, Mockito.times(1)).encode(any());
        verify(userRepository, Mockito.times(0)).findByLogin(any());
        assertEquals(expected, actual);
    }

    @Test
    void should_update_without_new_password() {

        //given
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .id(1L)
            .login("login")
            .oldPassword("old")
            .newPassword(null)
            .retypedPassword("new")
            .password("pass")
            .changePassword(true)
            .build();

        User expected = User.builder()
            .id(1L)
            .login("login")
            .password("pass")
            .build();

        //when
        when(userRepository.findByLogin(any())).thenReturn(expected);
        User actual = userService.updateUser(userUpdateDto);

        //then
        verify(passwordEncoder, Mockito.times(0)).encode(any());
        verify(userRepository, Mockito.times(1)).findByLogin(any());
        assertEquals(expected, actual);
    }
}
