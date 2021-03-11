package pl.jklata.budgetapp.dto.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.domain.enums.PaymentType;

@Getter
@Setter
@NoArgsConstructor
public class PaymentFilterDto {

    private Long id;
    private Long idForUser;
    private String title;
    private PaymentCategory paymentCategory;
    private PaymentType paymentType;
    private User user;
    //todo Reszta pól


}
