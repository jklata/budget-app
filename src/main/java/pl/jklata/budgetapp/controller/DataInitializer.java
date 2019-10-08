package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.*;
import pl.jklata.budgetapp.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final HashtagRepository hashtagRepository;
    private final TransactionCategoryRepository transactionCategoryRepository;

    public DataInitializer(UserRepository userRepository, AccountRepository accountRepository, BudgetRepository budgetRepository, TransactionRepository transactionRepository, HashtagRepository hashtagRepository, TransactionCategoryRepository transactionCategoryRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
        this.hashtagRepository = hashtagRepository;
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        User user1 = new User();
        user1.setLogin("login");
        user1.setPassword("1234");
        user1.setEmail("test@gmail.com");
        user1.setFirstName("Jan");
        user1.setLastName("Kowalski");

        Account account1 = new Account();
        account1.setName("Konto MBank");
        account1.setInitialBalance(new BigDecimal(35000.55));
        account1.setCurrency(Currency.getInstance("PLN"));
        account1.setUser(user1);
        account1.setAccountType(AccountType.BANK);

        Budget budget1 = new Budget();
        budget1.setName("budżet domowy");
        budget1.setBudgetValue(new BigDecimal(5000));

        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        user1.setAccounts(accounts);

        TransactionCategory tranCat1 = new TransactionCategory();
        tranCat1.setName("Samochód");
        tranCat1.setColor("#0040FF");

        TransactionCategory tranCat2 = new TransactionCategory();
        tranCat2.setName("Dom");
        tranCat2.setColor("#e83e8c");

        TransactionCategory tranCat3 = new TransactionCategory();
        tranCat3.setName("Jedzenie");
        tranCat3.setColor("#e83e8c");

        List<TransactionCategory> transactionCategories = new ArrayList<>(Arrays.asList(tranCat1, tranCat2, tranCat3));

        Hashtag ht1 = new Hashtag();
        ht1.setName("orlen");
        Hashtag ht2 = new Hashtag();
        ht2.setName("samochód");


        userRepository.save(user1);
        accountRepository.save(account1);
        transactionCategoryRepository.saveAll(transactionCategories);
        hashtagRepository.saveAll(new HashSet<>(Arrays.asList(ht1, ht2)));
        budgetRepository.save(budget1);

        Random r = new Random();
        for (int i = 0; i <15 ; i++) {

            Transaction transaction = new Transaction();
            transaction.setTransactionDate(LocalDate.of(r.nextInt(2019-2000)+2000,r.nextInt(12-1)+1,r.nextInt(30-1)+1));
            transaction.setInsertDate(LocalDate.now());
            transaction.setAmount(new BigDecimal(r.nextInt(3000-100)+100));
            transaction.setPayee("Odbiorca " + r.nextInt((i+1)+1));
            transaction.setTransactionCategory(transactionCategories.get(r.nextInt(4-1)));
            transaction.setTransactionType(TransactionType.EXPENSE);
            transaction.setAccount(account1);
            transaction.setBudget(budget1);
            transaction.setHashtags(new HashSet<>(Arrays.asList(ht2)));
            transactionRepository.save(transaction);
        }
    }
}
