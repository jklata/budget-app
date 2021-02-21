package pl.jklata.budgetapp.service;

import java.util.List;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Account;
import pl.jklata.budgetapp.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {


    private final AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
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
