package pl.jklata.budgetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.Payment;

import java.util.List;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT DISTINCT YEAR(transaction_date) FROM payment ORDER BY YEAR(transaction_date) DESC ",
            nativeQuery = true)
    List<Integer> getDistinctYearFromAllPayments();
}
