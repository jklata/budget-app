package pl.jklata.budgetapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
