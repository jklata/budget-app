package pl.jklata.budgetapp.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.domain.User;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Page<Payment> findAllByUser(User user, Pageable pageable);

    List<Payment> findAllByUser(User user);

    Optional<Payment> findByIdAndUser(Long id, User user);

    @Query("SELECT max(p.idForUser) FROM Payment p WHERE p.user = ?1")
    Long findMaxIdForUser(User user);

    @Query("SELECT DISTINCT YEAR(transaction_date) FROM Payment p WHERE p.user = ?1 ORDER BY YEAR(transaction_date) DESC")
    List<Integer> getDistinctYearFromAllPayments(User user);

    List<Payment> findAllByUserAndPaymentDateBetween(@NotNull User user, LocalDate dateFrom,
        LocalDate dateTo);

    Long countAllByUserAndPaymentCategory(User user, PaymentCategory paymentCategory);
}
