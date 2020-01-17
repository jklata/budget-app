package pl.jklata.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.PaymentType;
import pl.jklata.budgetapp.repository.PaymentRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    public Page<Payment> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Payment> paymentList = findAllReversed();
        List<Payment> list;

        if (paymentList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, paymentList.size());
            list = paymentList.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), paymentList.size());
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

    public List<Payment> findAllReversed() {

        return paymentRepository.findAll().stream()
                .sorted(Comparator.comparing(Payment::getId).reversed())
                .collect(Collectors.toList());
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }


}
