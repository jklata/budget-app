package pl.jklata.budgetapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.dto.UserDto;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto source) {
        return User.builder()
            .id(source.getId())
            .login(source.getLogin())
            .password(source.getPassword())
            .userRoles(source.getUserRoles())
            .permissions(source.getPermissions())
            .active(source.isActive())
            .build();
    }
}
