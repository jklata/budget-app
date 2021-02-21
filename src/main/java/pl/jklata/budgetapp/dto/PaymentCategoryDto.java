package pl.jklata.budgetapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaymentCategoryDto {

    private Long id;
    private String name;
    private String color;
    private Long paymentsCount;
}
