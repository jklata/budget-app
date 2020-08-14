package pl.jklata.budgetapp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.dto.UserUpdateDto;

@Component
public class UserEntityToUserUpdateDto implements Converter<User, UserUpdateDto> {

    private ModelMapper modelMapper;

    @Autowired
    public UserEntityToUserUpdateDto(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserUpdateDto convert(User source) {
        if (source == null) {
            return null;
        }

        return modelMapper.map(source, UserUpdateDto.class);
    }
}
