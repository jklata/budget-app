package pl.jklata.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Transaction;
import pl.jklata.budgetapp.domain.TransactionType;
import pl.jklata.budgetapp.repository.TransactionRepository;

import java.math.BigDecimal;


@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public void save(Transaction transaction){
        if (transaction.getTransactionType()== TransactionType.EXPENSE){
            transaction.setAmount(new BigDecimal(transaction.getAmount().floatValue()*-1));
        }
        transactionRepository.save(transaction);
    }


    public Iterable<Transaction> findAll(){
        return transactionRepository.findAll();
    }
}
