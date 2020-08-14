package pl.jklata.budgetapp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.dto.UserUpdateDto;

@Component
public class UserUpdateDtoToEntity implements Converter<UserUpdateDto, User> {

    private ModelMapper modelMapper;

    @Autowired
    public UserUpdateDtoToEntity(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User convert(UserUpdateDto source) {
        if (source == null) {
            return null;
        }

        return modelMapper.map(source, User.class);
    }
}
