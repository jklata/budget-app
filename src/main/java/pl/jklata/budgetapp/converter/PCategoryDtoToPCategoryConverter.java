package pl.jklata.budgetapp.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.dto.PaymentCategoryDto;
import pl.jklata.budgetapp.service.AuthUserService;

@Component
@RequiredArgsConstructor
public class PCategoryDtoToPCategoryConverter implements
    Converter<PaymentCategoryDto, PaymentCategory> {

    private final AuthUserService authUserService;

    @Override
    public PaymentCategory convert(PaymentCategoryDto paymentCategoryDto) {
        return PaymentCategory.builder()
            .id(paymentCategoryDto.getId())
            .name(paymentCategoryDto.getName())
            .color(paymentCategoryDto.getColor())
            .user(authUserService.getAuthenticatedUser())
            .build();
    }
}
