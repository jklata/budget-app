package pl.jklata.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.repository.PaymentRepository;
import pl.jklata.budgetapp.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private UserRepository userRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }


    public Page<Payment> findPaginated(Pageable pageable) {
        return paymentRepository.findAllByUser(getAuthenticatedUser(), pageable);
    }

    public Payment save(Payment payment) {
        if (payment.getPaymentType() == PaymentType.EXPENSE) {
            payment.setAmount(BigDecimal.valueOf(payment.getAmount().floatValue() * PaymentType.EXPENSE.getPaymentFactor()));
        }
        payment.setInsertDate(LocalDate.now());
        payment.setUser(getAuthenticatedUser());
        payment.setIdForUser(resolveNextIdForUser());
        return paymentRepository.save(payment);
    }
    public Payment saveDataInitializer(Payment payment) {
        if (payment.getPaymentType() == PaymentType.EXPENSE) {
            payment.setAmount(BigDecimal.valueOf(payment.getAmount().floatValue() * PaymentType.EXPENSE.getPaymentFactor()));
        }
        payment.setInsertDate(LocalDate.now());
        return paymentRepository.save(payment);
    }

    public List<Payment> findAllForAuthUser() {
        return paymentRepository.findAllByUser(getAuthenticatedUser());
    }

    public Payment findByIdForAuthUser(Long id) {
        return paymentRepository.findByIdAndUser(id, getAuthenticatedUser()).orElseThrow(NoSuchElementException::new);
    }

    public void deleteById(Long id) {
        if (isUserOwnsPayment(id)) {
            paymentRepository.deleteById(id);
        }
    }

    private User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = "";
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        }
        return userRepository.findByLogin(userName);
    }

    private boolean isUserOwnsPayment(Long id) {
        return findAllForAuthUser().stream().anyMatch(payment -> payment.getId().equals(id));
    }

    private Long resolveNextIdForUser(){
        Long lastId = paymentRepository.findMaxIdForUser(getAuthenticatedUser());
        return lastId+1;
    }

    public List<Integer> getDistinctYearFromAllPayments() {
        return paymentRepository.getDistinctYearFromAllPayments();
    }

}
