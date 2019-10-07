package pl.jklata.budgetapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
