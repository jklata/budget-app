package pl.jklata.budgetapp.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.*;
import pl.jklata.budgetapp.domain.enums.AccountType;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.domain.enums.Role;
import pl.jklata.budgetapp.repository.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Data
@Slf4j
@Component
@Profile("dev")
@PropertySource("classpath:application-dev.properties")
public class DataInitializer {

    @Value("${payments.number}")
    private int PAYMENTS_NUMBER;

    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BudgetRepository budgetRepository;
    private final PaymentRepository paymentRepository;
    private final HashtagRepository hashtagRepository;
    private final PaymentCategoryRepository paymentCategoryRepository;
    private final UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(PaymentService paymentService, UserRepository userRepository, AccountRepository accountRepository, BudgetRepository budgetRepository, PaymentRepository paymentRepository, HashtagRepository hashtagRepository, PaymentCategoryRepository paymentCategoryRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.paymentService = paymentService;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.budgetRepository = budgetRepository;
        this.paymentRepository = paymentRepository;
        this.hashtagRepository = hashtagRepository;
        this.paymentCategoryRepository = paymentCategoryRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    void init() {
        UserRole adminRole = new UserRole();
        adminRole.setRole(Role.ADMIN);
        UserRole standardUserRole = new UserRole();
        standardUserRole.setRole(Role.USER);
        UserRole premiumUserRole = new UserRole();
        premiumUserRole.setRole(Role.PREMIUM_USER);

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

        Account account1 = new Account();
        account1.setName("Konto MBank");
        account1.setInitialBalance(BigDecimal.valueOf(35000.55));
        account1.setCurrency(Currency.getInstance("PLN"));
        account1.setUser(user1);
        account1.setAccountType(AccountType.BANK);

        Budget budget1 = new Budget();
        budget1.setName("budżet domowy");
        budget1.setBudgetValue(BigDecimal.valueOf(5000));

        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        user1.setAccounts(accounts);

        PaymentCategory tranCat1 = new PaymentCategory();
        tranCat1.setName("Samochód");
        tranCat1.setColor("#0040FF");

        PaymentCategory tranCat2 = new PaymentCategory();
        tranCat2.setName("Dom");
        tranCat2.setColor("#e83e8c");

        PaymentCategory tranCat3 = new PaymentCategory();
        tranCat3.setName("Jedzenie");
        tranCat3.setColor("#e83e8c");

        List<PaymentCategory> paymentCategories = new ArrayList<>(Arrays.asList(tranCat1, tranCat2, tranCat3));

        Hashtag ht1 = new Hashtag();
        ht1.setName("orlen");
        Hashtag ht2 = new Hashtag();
        ht2.setName("samochód");

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
        for (int i = 0; i < PAYMENTS_NUMBER; i++) {

            Payment payment = new Payment();
            payment.setPaymentDate(LocalDate.of(r.nextInt(2021 - 2018) + 2018, r.nextInt(12 - 1) + 1, r.nextInt(25 - 1) + 1));
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
