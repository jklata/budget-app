package pl.jklata.budgetapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.PaymentCategory;

import java.util.Optional;

@Repository
public interface PaymentCategoryRepository extends CrudRepository<PaymentCategory, Long> {


    Optional<PaymentCategory> findByName(String name);
}
