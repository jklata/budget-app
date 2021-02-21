package pl.jklata.budgetapp.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.converter.PCategoryDtoToPCategoryConverter;
import pl.jklata.budgetapp.converter.PCategoryToPCategoryDtoConverter;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.dto.PaymentCategoryDto;
import pl.jklata.budgetapp.repository.PaymentCategoryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentCategoryService {

    private final PaymentCategoryRepository paymentCategoryRepository;
    private final PCategoryToPCategoryDtoConverter pCategoryToPCategoryDtoConverter;
    private final PCategoryDtoToPCategoryConverter pCategoryDtoToPCategoryConverter;
    private final PaymentService paymentService;
    private final AuthUserService authUserService;


    public List<PaymentCategoryDto> findAll() {
        List<PaymentCategory> paymentCategories = paymentCategoryRepository
            .findAllByUser(authUserService.getAuthenticatedUser());

        List<PaymentCategoryDto> paymentCategoryDtoList = paymentCategories.stream()
            .map(pCategoryToPCategoryDtoConverter::convert).collect(
                Collectors.toList());

        paymentCategoryDtoList.forEach(p -> p.setPaymentsCount(getPaymentsCount(p)));
        return paymentCategoryDtoList;
    }

    private Long getPaymentsCount(PaymentCategoryDto dto) {
        return paymentService
            .countPaymentsByPaymentCategory(pCategoryDtoToPCategoryConverter.convert(dto));
    }

    public PaymentCategory save(PaymentCategory paymentCategory) {

        if (!paymentCategoryRepository.findByName(paymentCategory.getName()).isPresent()) {
            paymentCategory.setUser(authUserService.getAuthenticatedUser());
            return paymentCategoryRepository.save(paymentCategory);
        } else {
            throw new EntityExistsException();
        }
    }

}
