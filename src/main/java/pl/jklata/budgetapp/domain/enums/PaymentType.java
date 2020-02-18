package pl.jklata.budgetapp.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentType {
    EXPENSE(-1),
    INCOME(1);

    int paymentFactor;
}
