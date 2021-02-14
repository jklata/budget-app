package pl.jklata.budgetapp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.Account;
import pl.jklata.budgetapp.domain.Budget;
import pl.jklata.budgetapp.domain.Hashtag;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.domain.UserRole;
import pl.jklata.budgetapp.domain.enums.AccountType;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.domain.enums.Role;
import pl.jklata.budgetapp.repository.AccountRepository;
import pl.jklata.budgetapp.repository.BudgetRepository;
import pl.jklata.budgetapp.repository.HashtagRepository;
import pl.jklata.budgetapp.repository.PaymentCategoryRepository;
import pl.jklata.budgetapp.repository.PaymentRepository;
import pl.jklata.budgetapp.repository.UserRepository;
import pl.jklata.budgetapp.repository.UserRoleRepository;

@Data
@AllArgsConstructor
@Slf4j
@Component
@Profile("dev")
public class DataInitializer {

    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BudgetRepository budgetRepository;
    private final PaymentRepository paymentRepository;
    private final HashtagRepository hashtagRepository;
    private final PaymentCategoryRepository paymentCategoryRepository;
    private final UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    void init() {
        UserRole adminRole = UserRole.builder()
            .role(Role.ADMIN).build();
        UserRole standardUserRole = UserRole.builder()
            .role(Role.USER).build();
        UserRole premiumUserRole = UserRole.builder()
            .role(Role.PREMIUM_USER).build();

        userRoleRepository.save(adminRole);
        userRoleRepository.save(standardUserRole);
        userRoleRepository.save(premiumUserRole);

        User user1 = User.builder()
            .login("user")
            .password(passwordEncoder.encode("user123"))
            .email("test@gmail.com")
            .firstName("Jan")
            .lastName("Kowalski")
            .userRoles(Collections.singleton(standardUserRole))
            .permissions("STANDARD")
            .active(true)
            .build();

        User admin = User.builder()
            .login("admin")
            .password(passwordEncoder.encode("admin123"))
            .email("test2@gmail.com")
            .firstName("Admin")
            .lastName("Admiński")
            .userRoles(Collections.singleton(adminRole))
            .permissions("ALL")
            .active(true)
            .build();

        User user3 = User.builder()
            .login("super")
            .password(passwordEncoder.encode("super123"))
            .email("test33@gmail.com")
            .firstName("Jan")
            .lastName("Kowalski")
            .userRoles(Collections.singleton(standardUserRole))
            .permissions("ALL")
            .active(true)
            .build();

        Account account1 = Account.builder()
            .name("Konto MBank")
            .initialBalance(BigDecimal.valueOf(35000.55))
            .currency(Currency.getInstance("PLN"))
            .user(user1)
            .accountType(AccountType.BANK)
            .build();

        Budget budget1 = Budget.builder()
            .name("budżet domowy")
            .budgetValue(BigDecimal.valueOf(5000))
            .build();

        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        user1.setAccounts(accounts);

        PaymentCategory tranCat1 = PaymentCategory.builder()
            .name("Samochód")
            .color("#0040FF")
            .build();

        PaymentCategory tranCat2 = PaymentCategory.builder()
            .name("Dom")
            .color("#e83e8c")
            .build();

        PaymentCategory tranCat3 = PaymentCategory.builder()
            .name("Jedzenie")
            .color("#e83e8c")
            .build();

        List<PaymentCategory> paymentCategories = new ArrayList<>(
            Arrays.asList(tranCat1, tranCat2, tranCat3));

        Hashtag ht1 = Hashtag.builder()
            .name("orlen")
            .build();
        Hashtag ht2 = Hashtag.builder()
            .name("samochód")
            .build();

        userRepository.save(user1);
        userRepository.save(admin);
        userRepository.save(user3);
        accountRepository.save(account1);
        paymentCategoryRepository.saveAll(paymentCategories);
        hashtagRepository.saveAll(new HashSet<>(Arrays.asList(ht1, ht2)));
        budgetRepository.save(budget1);

        Random r = new Random();
        Long idByUser1 = 0L;
        Long idByUser2 = 0L;
        for (int i = 0; i < 10000; i++) {

            Payment payment = new Payment();
            payment.setPaymentDate(LocalDate
                .of(r.nextInt(2021 - 2018) + 2018, r.nextInt(12 - 1) + 1, r.nextInt(25 - 1) + 1));
            payment.setInsertDate(LocalDate.now());
            payment.setAmount(BigDecimal.valueOf((r.nextInt(3000 - 100) + 100) * 0.97));
            payment.setTitle("Odbiorca " + ((r.nextInt(i + 1)) + 1));
            payment.setPaymentCategory(paymentCategories.get(r.nextInt(4 - 1)));
            int rand = r.nextInt((1) + 1);
            if (rand == 1) {
                payment.setPaymentType(PaymentType.EXPENSE);
            } else {
                payment.setPaymentType(PaymentType.INCOME);
            }
            payment.setAccount(account1);
            payment.setBudget(budget1);
            payment.setHashtags(new HashSet<>(Arrays.asList(ht2)));
            if (i % 2 == 0) {
                idByUser1++;
                payment.setIdForUser(idByUser1);
                payment.setUser(admin);
            } else {
                idByUser2++;
                payment.setIdForUser(idByUser2);
                payment.setUser(user1);
            }
            paymentService.saveDataInitializer(payment);
        }
    }
}
