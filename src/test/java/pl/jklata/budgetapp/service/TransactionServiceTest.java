package pl.jklata.budgetapp.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.jklata.budgetapp.domain.Transaction;
import pl.jklata.budgetapp.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    public void save() {
        Transaction transaction = new Transaction();
        List<Transaction> transactionsData = new ArrayList<>();
        transactionsData.add(transaction);

        when(transactionRepository.findAll()).thenReturn(transactionsData);

        List<Transaction> transactions = (List<Transaction>) transactionService.findAll();

        assertEquals(transactions.size(), 1);
        verify(transactionRepository, times(1)).findAll();
    }
}