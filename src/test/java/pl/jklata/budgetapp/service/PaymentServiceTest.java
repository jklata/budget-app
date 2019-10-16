package pl.jklata.budgetapp.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    PaymentService paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        paymentService = new PaymentService(paymentRepository);
    }

    @Test
    public void save() {
        Payment payment = new Payment();
        List<Payment> transactionsData = new ArrayList<>();
        transactionsData.add(payment);

        when(paymentRepository.findAll()).thenReturn(transactionsData);

        List<Payment> payments = (List<Payment>) paymentService.findAll();

        assertEquals(payments.size(), 1);
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteById() throws Exception{
        Long idToDelete = 2L;
        paymentService.deleteById(idToDelete);
        verify(paymentRepository, times(1)).deleteById(anyLong());
    }
}