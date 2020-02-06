package pl.jklata.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.repository.PaymentRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    public Page<Payment> findPaginated(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    public Payment save(Payment payment) {
        if (payment.getPaymentType() == PaymentType.EXPENSE) {
            payment.setAmount(BigDecimal.valueOf(payment.getAmount().floatValue() * -1));
        }
        payment.setInsertDate(LocalDate.now());
        return paymentRepository.save(payment);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }


}
