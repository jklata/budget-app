package pl.jklata.budgetapp.service;

import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Transaction;
import pl.jklata.budgetapp.domain.TransactionCategory;
import pl.jklata.budgetapp.repository.TransactionCategoryRepository;

import java.util.List;
import java.util.Set;

@Service
public class TransactionCategoryService {

    TransactionCategoryRepository transactionCategoryRepository;

    public TransactionCategoryService(TransactionCategoryRepository transactionCategoryRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
    }


    public List<TransactionCategory> findAll(){
        return (List<TransactionCategory>)transactionCategoryRepository.findAll();
    }
}
