package pl.jklata.budgetapp.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.service.AccountService;
import pl.jklata.budgetapp.service.BudgetService;
import pl.jklata.budgetapp.service.PaymentCategoryService;
import pl.jklata.budgetapp.service.PaymentService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PaymentControllerTest {


    @Mock
    PaymentService paymentService;
    PaymentCategoryService paymentCategoryService;
    AccountService accountService;
    BudgetService budgetService;

    PaymentController paymentController;
    MockMvc mockMvc;

    @Before

    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        paymentController = new PaymentController(paymentCategoryService, paymentService, accountService, budgetService);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }


    @Test
    public void testGetPayment() throws Exception {

        Payment payment = new Payment();
        payment.setId(1L);

        when(paymentService.findById(anyLong())).thenReturn(payment);

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

        verify(paymentService, times(1)).deleteById(anyLong());
    }
}
