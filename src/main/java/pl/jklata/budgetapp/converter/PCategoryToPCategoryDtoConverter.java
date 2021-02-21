package pl.jklata.budgetapp.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.dto.PaymentCategoryDto;

@RequiredArgsConstructor
@Component
public class PCategoryToPCategoryDtoConverter implements
    Converter<PaymentCategory, PaymentCategoryDto> {

    private final ModelMapper modelMapper;

    @Override
    public PaymentCategoryDto convert(PaymentCategory paymentCategory) {
        return modelMapper.map(paymentCategory, PaymentCategoryDto.class);
    }
}
