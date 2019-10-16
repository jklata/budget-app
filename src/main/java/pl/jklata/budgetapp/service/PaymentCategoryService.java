package pl.jklata.budgetapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.repository.PaymentCategoryRepository;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class PaymentCategoryService {

    PaymentCategoryRepository paymentCategoryRepository;

    public PaymentCategoryService(PaymentCategoryRepository paymentCategoryRepository) {
        this.paymentCategoryRepository = paymentCategoryRepository;
    }


    public List<PaymentCategory> findAll() {

        return  StreamSupport
                .stream(paymentCategoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public PaymentCategory findByName(String name) {
        return paymentCategoryRepository.findByName(name).get();
    }

    public PaymentCategory save(PaymentCategory paymentCategory) {

        if (!paymentCategoryRepository.findByName(paymentCategory.getName()).isPresent()) {
            return paymentCategoryRepository.save(paymentCategory);
        } else {
            throw new EntityExistsException();
        }
    }

}
