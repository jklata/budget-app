package pl.jklata.budgetapp.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.dto.UserUpdateDto;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class UserUpdateDtoToEntity implements Converter<UserUpdateDto, User> {

    @Override
    public User convert(UserUpdateDto source) {
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
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setAvatar(getByteArrayFromLogoFile(source.getAvatar()));
        user.setEmail(source.getEmail());
        return user;

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
