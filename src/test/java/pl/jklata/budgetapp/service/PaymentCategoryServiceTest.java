package pl.jklata.budgetapp.service;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jklata.budgetapp.BudgetAppApplication;
import pl.jklata.budgetapp.converter.PCategoryDtoToPCategoryConverter;
import pl.jklata.budgetapp.converter.PCategoryToPCategoryDtoConverter;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.dto.PaymentCategoryDto;
import pl.jklata.budgetapp.repository.PaymentCategoryRepository;

@SpringBootTest(classes = BudgetAppApplication.class)
@ExtendWith(MockitoExtension.class)
class PaymentCategoryServiceTest {

    private PaymentCategoryService paymentCategoryService;
    @Mock
    private PaymentCategoryRepository paymentCategoryRepository;
    @Mock
    private PaymentService paymentService;
    @Mock
    private AuthUserService authUserService;


    @BeforeEach
    void setup() {
        ModelMapper modelMapper = new ModelMapper();
        PCategoryDtoToPCategoryConverter pCategoryDtoToPCategoryConverter = new PCategoryDtoToPCategoryConverter(
            authUserService);
        PCategoryToPCategoryDtoConverter pCategoryToPCategoryDtoConverter = new PCategoryToPCategoryDtoConverter(
            modelMapper);
        paymentCategoryService = new PaymentCategoryService(paymentCategoryRepository,
            pCategoryToPCategoryDtoConverter, pCategoryDtoToPCategoryConverter, paymentService,
            authUserService);
    }

    @Test
    void shouldReturnListOfDtoWithCountValues() {
        //given
        List<PaymentCategoryDto> expected = createListOfPaymentCategoryDto();
        List<PaymentCategory> listOfPaymentCategory = createListOfPaymentCategory();
        User user1 = User.builder().id(1L).build();
        Mockito.when(authUserService.getAuthenticatedUser()).thenReturn(user1);
        Mockito.when(paymentCategoryRepository.findAllByUser(user1))
            .thenReturn(listOfPaymentCategory);
        Mockito.when(paymentService.countPaymentsByPaymentCategory(listOfPaymentCategory.get(0)))
            .thenReturn(250L);
        Mockito.when(paymentService.countPaymentsByPaymentCategory(listOfPaymentCategory.get(1)))
            .thenReturn(555L);

        //when

        List<PaymentCategoryDto> actual = paymentCategoryService.findAll();

        //then
        Assertions.assertEquals(expected.get(0), actual.get(0));
        Assertions.assertEquals(expected.get(1), actual.get(1));
        Assertions.assertEquals(expected, actual);
    }

    //TODO: dopisać testy
    private List<PaymentCategory> createListOfPaymentCategory() {
        return Arrays.asList(
            PaymentCategory.builder()
                .id(1L)
                .color("Kolor1")
                .name("Kategoria1")
                .user(User.builder().id(1L).build())
                .build(),
            PaymentCategory.builder()
                .id(2L)
                .color("Kolor2")
                .name("Kategoria2")
                .user(User.builder().id(1L).build())
                .build()
        );
    }

    private List<PaymentCategoryDto> createListOfPaymentCategoryDto() {
        return Arrays.asList(
            PaymentCategoryDto.builder()
                .id(1L)
                .color("Kolor1")
                .name("Kategoria1")
                .paymentsCount(250L)
                .build(),
            PaymentCategoryDto.builder()
                .id(2L)
                .color("Kolor2")
                .name("Kategoria2")
                .paymentsCount(555L)
                .build()
        );
    }
}
