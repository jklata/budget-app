package pl.jklata.budgetapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.TransactionCategory;

import java.util.Optional;

@Repository
public interface TransactionCategoryRepository extends CrudRepository<TransactionCategory, Long> {


    Optional<TransactionCategory> findByName(String name);
}
