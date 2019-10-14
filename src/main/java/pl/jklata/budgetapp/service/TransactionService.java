package pl.jklata.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Transaction;
import pl.jklata.budgetapp.domain.TransactionType;
import pl.jklata.budgetapp.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public Page<Transaction> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Transaction> transactionList = findAllreversed();
        List<Transaction> list;

        if (transactionList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, transactionList.size());
            list = transactionList.subList(startItem, toIndex);
        }

        Page<Transaction> transactionPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize),transactionList.size());
        return  transactionPage;
    }


    public Transaction save(Transaction transaction) {
        if (transaction.getTransactionType() == TransactionType.EXPENSE) {
            transaction.setAmount(new BigDecimal(transaction.getAmount().floatValue() * -1));
        }
        transaction.setInsertDate(LocalDate.now());
        return transactionRepository.save(transaction);
    }


    public Iterable<Transaction> findAll() {
        return transactionRepository.findAll();
    }


    public List<Transaction> findAllreversed() {

        List<Transaction> transactionList =  StreamSupport
                .stream(transactionRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparing(Transaction::getId).reversed())
                .collect(Collectors.toList());
        return transactionList;
    }

    public Transaction findById(Long id) {

        return transactionRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }


}
