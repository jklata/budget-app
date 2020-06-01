package pl.jklata.budgetapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.*;
import pl.jklata.budgetapp.domain.enums.AccountType;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.repository.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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

    public DataInitializer(PaymentService paymentService, UserRepository userRepository, AccountRepository accountRepository,
                           BudgetRepository budgetRepository, PaymentRepository paymentRepository,
                           HashtagRepository hashtagRepository, PaymentCategoryRepository paymentCategoryRepository,
                           UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
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

        User user1 = new User();
        user1.setLogin("user");
        user1.setPassword(passwordEncoder.encode("user123"));
        user1.setEmail("test@gmail.com");
        user1.setFirstName("Jan");
        user1.setLastName("Kowalski");
        user1.setRoles("USER");
        user1.setPermissions("STANDARD");
        user1.setActive(true);

        User user2 = new User();
        user2.setLogin("admin");
        user2.setPassword(passwordEncoder.encode("admin123"));
        user2.setEmail("test2@gmail.com");
        user2.setFirstName("Admin");
        user2.setLastName("Admiński");
        user2.setRoles("ADMIN");
        user2.setPermissions("ALL");
        user2.setActive(true);

        User user3 = new User();
        user3.setLogin("super");
        user3.setPassword(passwordEncoder.encode("super123"));
        user3.setEmail("test33@gmail.com");
        user3.setFirstName("Jan");
        user3.setLastName("Kowalski");
        user3.setRoles("SUPER");
        user3.setPermissions("ALL");
        user3.setActive(true);

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
        userRepository.save(user2);
        userRepository.save(user3);
        accountRepository.save(account1);
        paymentCategoryRepository.saveAll(paymentCategories);
        hashtagRepository.saveAll(new HashSet<>(Arrays.asList(ht1, ht2)));
        budgetRepository.save(budget1);

        Random r = new Random();
        for (int i = 0; i < 50; i++) {

            Payment payment = new Payment();
            payment.setPaymentDate(LocalDate.of(r.nextInt(2020 - 2018) + 2018, r.nextInt(12 - 1) + 1, r.nextInt(25 - 1) + 1));
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
            paymentService.save(payment);
        }
    }
}
