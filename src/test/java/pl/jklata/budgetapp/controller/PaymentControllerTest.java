package pl.jklata.budgetapp.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.jklata.budgetapp.dto.PaymentDto;
import pl.jklata.budgetapp.service.*;

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
    CsvService csvService;

    PaymentController paymentController;
    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        paymentController = new PaymentController(paymentCategoryService, paymentService, accountService, budgetService, csvService);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    public void testGetPayment() throws Exception {

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(1L);

        when(paymentService.findByIdForAuthUser(anyLong())).thenReturn(paymentDto);

        mockMvc.perform(get("/payments/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("payments/paymentShow"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(get("/payments/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/payments/"));

        verify(paymentService, times(1)).deleteById(anyLong());
    }
}
