package pl.jklata.budgetapp.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.jklata.budgetapp.domain.Transaction;
import pl.jklata.budgetapp.repository.TransactionCategoryRepository;
import pl.jklata.budgetapp.service.TransactionService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransactionControllerTest {


    @Mock
    TransactionService transactionService;
    TransactionCategoryRepository transactionCategoryRepository;

    TransactionController transactionController;
    MockMvc mockMvc;

    @Before

    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        transactionController = new TransactionController(transactionCategoryRepository, transactionService);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }


    @Test
    public void testGetTransaction() throws Exception {

        Transaction transaction = new Transaction();
        transaction.setId(1L);

        when(transactionService.findById(anyLong())).thenReturn(transaction);

        mockMvc.perform(get("/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("transactionShow"))
                .andExpect(model().attributeExists("transaction"));
    }


    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(get("/1/delete"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("add-transaction"));

        verify(transactionService, times(1)).deleteById(anyLong());
    }
}
