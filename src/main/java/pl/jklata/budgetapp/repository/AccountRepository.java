package pl.jklata.budgetapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
}
