package pl.jklata.budgetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.Payment;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
