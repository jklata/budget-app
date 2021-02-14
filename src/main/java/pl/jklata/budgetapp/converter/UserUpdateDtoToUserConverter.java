package pl.jklata.budgetapp.converter;

import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.dto.UserUpdateDto;

@Slf4j
@Component
public class UserUpdateDtoToUserConverter implements Converter<UserUpdateDto, User> {

    @Override
    public User convert(UserUpdateDto source) {
        if (source == null) {
            return null;
        }

        return User.builder()
            .id(source.getId())
            .login(source.getLogin())
            .password(source.getPassword())
            .userRoles(source.getUserRoles())
            .permissions(source.getPermissions())
            .active(source.isActive())
            .firstName(source.getFirstName())
            .lastName(source.getLastName())
            .avatar(getByteArrayFromLogoFile(source.getAvatar()))
            .email(source.getEmail())
            .build();

    }

    private byte[] getByteArrayFromLogoFile(MultipartFile logo) {
        byte[] byteArray = null;
        if (Objects.nonNull(logo) && !logo.isEmpty()) {
            try {
                byteArray = logo.getBytes();
            } catch (IOException e) {
                log.error("Get byte array from multipartFile went wrong.", e);
            }
        }
        return byteArray;
    }

}
