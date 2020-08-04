package pl.jklata.budgetapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import pl.jklata.budgetapp.domain.Account;
import pl.jklata.budgetapp.domain.Budget;
import pl.jklata.budgetapp.domain.Hashtag;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.domain.enums.PaymentType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class PaymentDto {

    private Long id;

    @NotNull(message = "{validator.invalid.null}")
    private Long idForUser;

    @NotNull(message = "{validator.invalid.null}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate insertDate;

    @NotNull(message = "{validator.invalid.null}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{validator.bounds.amount}")
    private BigDecimal amount;

    private String title;

    private PaymentCategory paymentCategory;

    @NotNull(message = "{validator.invalid.null}")
    private PaymentType paymentType;
    private Budget budget;
    private Set<Hashtag> hashtags;
    private Account account;

    public String getValuesForCsv() {
        String coma = ", ";
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(coma);
        sb.append(paymentDate);
        sb.append(coma);
        sb.append(amount);

        return sb.toString();
    }

    public BigDecimal getAmountWithSign() {
        if (paymentType.equals(PaymentType.INCOME)) {
            return amount;
        }
        if (paymentType.equals(PaymentType.EXPENSE)) {
            return amount.negate();
        }else return amount;    //Prepared for other payment types
    }


}
