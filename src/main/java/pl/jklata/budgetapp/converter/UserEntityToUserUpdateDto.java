package pl.jklata.budgetapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.dto.ImageMultipartFile;
import pl.jklata.budgetapp.dto.UserUpdateDto;

@Component
public class UserEntityToUserUpdateDto implements Converter<User, UserUpdateDto> {

    @Override
    public UserUpdateDto convert(User source) {
        if (source == null) {
            return null;
        }


        final UserUpdateDto user = new UserUpdateDto();
        user.setId(source.getId());
        user.setLogin(source.getLogin());
        user.setPassword(source.getPassword());
        user.setUserRoles(source.getUserRoles());
        user.setPermissions(source.getPermissions());
        user.setActive(source.isActive());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setAvatar(getMultipartFileFromByteArray(source));
        user.setEmail(source.getEmail());
        return user;

    }

    private static MultipartFile getMultipartFileFromByteArray(User user) {
        String name = "logoImage.png";
        byte[] imageBytes = user.getAvatar();
        return new ImageMultipartFile(imageBytes, name);
    }

}
