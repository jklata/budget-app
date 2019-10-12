package pl.jklata.budgetapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.Budget;

import java.util.Optional;

@Repository
public interface BudgetRepository extends CrudRepository<Budget, Long> {

    Optional<Budget> findByName(String name);

}
