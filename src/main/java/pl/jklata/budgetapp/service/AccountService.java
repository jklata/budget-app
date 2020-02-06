package pl.jklata.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Account;
import pl.jklata.budgetapp.repository.AccountRepository;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountService {


    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findByName(String name) {
        return accountRepository.findByName(name).get();
    }

    public List<Account> findAll() {
        return StreamSupport
                .stream(accountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Account save(Account account) {

        // todo: refactor -> @Column(unique=true)
        if (!accountRepository.findByName(account.getName()).isPresent()) {
            return accountRepository.save(account);
        } else {
            throw new EntityExistsException();
        }
    }

}
