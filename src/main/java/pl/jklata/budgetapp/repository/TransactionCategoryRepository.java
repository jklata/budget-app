package pl.jklata.budgetapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.Account;
import pl.jklata.budgetapp.domain.TransactionCategory;

@Repository
public interface TransactionCategoryRepository extends CrudRepository<TransactionCategory, Long> {
}