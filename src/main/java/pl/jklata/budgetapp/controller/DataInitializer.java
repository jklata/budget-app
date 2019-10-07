package pl.jklata.budgetapp.controller;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.domain.*;
import pl.jklata.budgetapp.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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

        Hashtag ht1 = new Hashtag();
        ht1.setName("orlen");
        Hashtag ht2 = new Hashtag();
        ht2.setName("samochód");


        Transaction transaction1 = new Transaction();
        transaction1.setTransactionDate(LocalDate.of(2010,4,13));
        transaction1.setInsertDate(LocalDate.now());
        transaction1.setAmount(new BigDecimal(253.12));
        transaction1.setPayee("ORLEN paliwo");
        transaction1.setTransactionCategory(tranCat1);
        transaction1.setTransactionType(TransactionType.EXPENSE);
        transaction1.setAccount(account1);
        transaction1.setBudget(budget1);
        transaction1.setHashtags(new HashSet<>(Arrays.asList(ht1, ht2)));

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionDate(LocalDate.of(2014,2,27));
        transaction2.setInsertDate(LocalDate.now());
        transaction2.setAmount(new BigDecimal(139.42));
        transaction2.setPayee("SHELL paliwo");
        transaction2.setTransactionCategory(tranCat1);
        transaction2.setTransactionType(TransactionType.EXPENSE);
        transaction2.setAccount(account1);
        transaction2.setBudget(budget1);
        transaction2.setHashtags(new HashSet<>(Arrays.asList(ht2)));

        userRepository.save(user1);
        accountRepository.save(account1);
        transactionCategoryRepository.save(tranCat1);
        hashtagRepository.saveAll(new HashSet<>(Arrays.asList(ht1, ht2)));
        budgetRepository.save(budget1);
        transactionRepository.saveAll(new ArrayList<>(Arrays.asList(transaction1, transaction2)));


    }


}
