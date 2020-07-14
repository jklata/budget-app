package pl.jklata.budgetapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.dto.UserDto;

@Component
public class UserDtoToEntity implements Converter<UserDto, User> {
    @Override
    public User convert(UserDto source) {
        if (source == null) {
            return null;
        }

        final User user = new User();
        user.setId(source.getId());
        user.setLogin(source.getLogin());
        user.setPassword(source.getPassword());
        user.setUserRoles(source.getUserRoles());
        user.setPermissions(source.getPermissions());
        user.setActive(source.isActive());
        return user;
    }
}
