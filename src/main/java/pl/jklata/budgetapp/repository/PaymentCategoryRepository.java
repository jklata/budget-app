package pl.jklata.budgetapp.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.domain.User;

@Repository
public interface PaymentCategoryRepository extends JpaRepository<PaymentCategory, Long> {

    Optional<PaymentCategory> findByName(String name);

    List<PaymentCategory> findAllByUser(User user);
}
