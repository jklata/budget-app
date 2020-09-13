package pl.jklata.budgetapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.aop.LogEvent;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.dto.PaymentDto;
import pl.jklata.budgetapp.dto.converter.PaymentDtoToEntity;
import pl.jklata.budgetapp.dto.converter.PaymentEntityToDto;
import pl.jklata.budgetapp.repository.PaymentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private AuthUserService authUserService;
    private PaymentDtoToEntity paymentDtoToEntity;
    private PaymentEntityToDto paymentEntityToDto;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, AuthUserService authUserService, PaymentDtoToEntity paymentDtoToEntity, PaymentEntityToDto paymentEntityToDto) {
        this.paymentRepository = paymentRepository;
        this.authUserService = authUserService;
        this.paymentDtoToEntity = paymentDtoToEntity;
        this.paymentEntityToDto = paymentEntityToDto;
    }


    public Page<PaymentDto> findPaginated(Pageable pageable) {
        return paymentRepository.findAllByUser(authUserService.getAuthenticatedUser(), pageable).map(payment -> paymentEntityToDto.convert(payment));
    }

    @LogEvent
    public Payment save(PaymentDto paymentDto) {
        Payment payment = paymentDtoToEntity.convert(paymentDto);
        payment.setInsertDate(LocalDate.now());
        payment.setUser(authUserService.getAuthenticatedUser());
        if (payment.getId() == null) {
            payment.setIdForUser(resolveNextIdForUser());
        }
        return paymentRepository.save(payment);
    }

    public Payment saveDataInitializer(Payment payment) {
        payment.setInsertDate(LocalDate.now());
        if (payment.getPaymentType() == PaymentType.EXPENSE && (payment.getAmount().signum() == 1)) {
            payment.setAmount(payment.getAmount().negate());
        }
        return paymentRepository.save(payment);
    }

    public List<PaymentDto> findAllForAuthUser() {
        return paymentRepository.findAllByUser(authUserService.getAuthenticatedUser()).stream()
                .map(payment -> paymentEntityToDto.convert(payment))
                .collect(Collectors.toList());
    }

    public PaymentDto findByIdForAuthUser(Long id) {
        Payment payment = paymentRepository.findByIdAndUser(id, authUserService.getAuthenticatedUser()).orElseThrow(NoSuchElementException::new);
        return paymentEntityToDto.convert(payment);
    }

    public void deleteById(Long id) {
        if (isUserOwnsPayment(id)) {
            paymentRepository.deleteById(id);
        }
    }

    private boolean isUserOwnsPayment(Long id) {
        try {
            return Objects.nonNull(findByIdForAuthUser(id));
        } catch (NoSuchElementException e) {
            log.debug("Authenticated user does not owns payment of id: {}, or it has been already deleted", id);
            return false;
        }
    }

    public Long resolveNextIdForUser() {
        Long lastId = paymentRepository.findMaxIdForUser(authUserService.getAuthenticatedUser());
        if (lastId == null) {
            return 1L;
        }
        return lastId + 1;
    }


}
