package pl.jklata.budgetapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Transaction;
import pl.jklata.budgetapp.domain.TransactionCategory;
import pl.jklata.budgetapp.repository.TransactionCategoryRepository;

import javax.persistence.EntityExistsException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class TransactionCategoryService {

    TransactionCategoryRepository transactionCategoryRepository;

    public TransactionCategoryService(TransactionCategoryRepository transactionCategoryRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
    }


    public List<TransactionCategory> findAll() {

        return  StreamSupport
                .stream(transactionCategoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public TransactionCategory findByName(String name) {
        return transactionCategoryRepository.findByName(name).get();
    }

    public TransactionCategory save(TransactionCategory transactionCategory) {

        if (!transactionCategoryRepository.findByName(transactionCategory.getName()).isPresent()) {
            return transactionCategoryRepository.save(transactionCategory);
        } else {
            throw new EntityExistsException();
        }
    }

}
