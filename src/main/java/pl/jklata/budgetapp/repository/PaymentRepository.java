package pl.jklata.budgetapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Page<Payment> findAllByUser(User user, Pageable pageable);

    List<Payment> findAllByUser(User user);

    Optional<Payment> findByIdAndUser(Long id, User user);

    @Query("SELECT max(p.idForUser) FROM Payment p WHERE p.user = ?1")
    Long findMaxIdForUser(User user);


}
