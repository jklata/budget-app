package pl.jklata.budgetapp.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import pl.jklata.budgetapp.domain.Account;
import pl.jklata.budgetapp.domain.Budget;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.domain.enums.PaymentType;

public class PaymentsDB {

    public Payment payment_1 = new Payment();

    {
        payment_1.setId(1L);
        payment_1.setIdForUser(1L);
        payment_1.setUser(new User());
        payment_1.setPaymentDate(LocalDate.of(2020, 04, 15));
        payment_1.setInsertDate(LocalDate.of(2020, 05, 20));
        payment_1.setAmount(BigDecimal.valueOf(-350));
        payment_1.setTitle("Płatność nr:" + payment_1.getId());
        payment_1.setPaymentCategory(new PaymentCategory());
        payment_1.setPaymentType(PaymentType.EXPENSE);
        payment_1.setBudget(new Budget());
        payment_1.setHashtags(new HashSet<>());
        payment_1.setAccount(new Account());
    }

    public Payment payment_2 = new Payment();

    {
        payment_2.setId(2L);
        payment_2.setIdForUser(2L);
        payment_2.setUser(new User());
        payment_2.setPaymentDate(LocalDate.of(2019, 04, 15));
        payment_2.setInsertDate(LocalDate.of(2019, 05, 20));
        payment_2.setAmount(BigDecimal.valueOf(250));
        payment_2.setTitle("Płatność nr:" + payment_2.getId());
        payment_2.setPaymentCategory(new PaymentCategory());
        payment_2.setPaymentType(PaymentType.INCOME);
        payment_2.setBudget(new Budget());
        payment_2.setHashtags(new HashSet<>());
        payment_2.setAccount(new Account());
    }
}
