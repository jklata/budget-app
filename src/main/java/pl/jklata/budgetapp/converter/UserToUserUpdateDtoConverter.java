package pl.jklata.budgetapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.dto.ImageMultipartFile;
import pl.jklata.budgetapp.dto.UserUpdateDto;

@Component
public class UserToUserUpdateDtoConverter implements Converter<User, UserUpdateDto> {

    @Override
    public UserUpdateDto convert(User source) {
        if (source == null) {
            return null;
        }

        final UserUpdateDto user = UserUpdateDto.builder()
            .id(source.getId())
            .login(source.getLogin())
            .password(source.getPassword())
            .userRoles(source.getUserRoles())
            .permissions(source.getPermissions())
            .active(source.isActive())
            .firstName(source.getFirstName())
            .lastName(source.getLastName())
            .avatar(getMultipartFileFromByteArray(source))
            .email(source.getEmail())
            .build();
        return user;

    }

    private static MultipartFile getMultipartFileFromByteArray(User user) {
        String name = "logoImage.png";
        byte[] imageBytes = user.getAvatar();
        return new ImageMultipartFile(imageBytes, name);
    }

}
