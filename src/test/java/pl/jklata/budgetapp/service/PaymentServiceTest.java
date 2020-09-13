package pl.jklata.budgetapp.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.dto.PaymentDto;
import pl.jklata.budgetapp.dto.converter.PaymentDtoToEntity;
import pl.jklata.budgetapp.dto.converter.PaymentEntityToDto;
import pl.jklata.budgetapp.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    PaymentService paymentService;

    @Mock
    PaymentRepository paymentRepository;
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        paymentService = new PaymentService(paymentRepository, authUserService, paymentDtoToEntity, paymentEntityToDto);
    }

    @Test
    public void save() {
        Payment payment = new Payment();
        List<Payment> transactionsData = new ArrayList<>();
        transactionsData.add(payment);

        when(paymentRepository.findAll()).thenReturn(transactionsData);

        List<Payment> payments = (List<Payment>) paymentService.findAllForAuthUser();

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