package pl.jklata.budgetapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.converter.PaymentDtoToPaymentConverter;
import pl.jklata.budgetapp.converter.PaymentToPaymentDtoConverter;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.PaymentCategory;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.dto.PaymentDto;
import pl.jklata.budgetapp.repository.PaymentRepository;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AuthUserService authUserService;
    private final PaymentDtoToPaymentConverter paymentDtoToPaymentConverter;
    private final PaymentToPaymentDtoConverter paymentToPaymentDtoConverter;

    public Page<PaymentDto> findPaginated(Pageable pageable) {
        return paymentRepository.findAllByUser(authUserService.getAuthenticatedUser(), pageable)
            .map(payment -> paymentToPaymentDtoConverter
                .convert(payment));
    }

    public Payment save(PaymentDto paymentDto) {
        Payment payment = paymentDtoToPaymentConverter.convert(paymentDto);
        payment.setInsertDate(LocalDate.now());
        payment.setUser(authUserService.getAuthenticatedUser());
        if (payment.getId() == null) {
            payment.setIdForUser(resolveNextIdForUser());
        }
        Payment persistedPayment = paymentRepository.save(payment);
        return persistedPayment;
    }

    public Payment saveDataInitializer(Payment payment) {
        payment.setInsertDate(LocalDate.now());
        if (payment.getPaymentType() == PaymentType.EXPENSE && (payment.getAmount().signum()
            == 1)) {
            payment.setAmount(payment.getAmount().negate());
        }
        return paymentRepository.save(payment);
    }

    public List<PaymentDto> findAllForAuthUser() {
        return paymentRepository.findAllByUser(authUserService.getAuthenticatedUser()).stream()
            .map(payment -> paymentToPaymentDtoConverter.convert(payment))
            .collect(Collectors.toList());
    }

    public PaymentDto findByIdForAuthUser(Long id) {
        Payment payment = paymentRepository
            .findByIdAndUser(id, authUserService.getAuthenticatedUser())
            .orElseThrow(NoSuchElementException::new);
        return paymentToPaymentDtoConverter.convert(payment);
    }

    public void deleteById(Long id) {
        if (isUserOwnsPayment(id)) {
            paymentRepository.deleteById(id);
        }
    }

    private boolean isUserOwnsPayment(Long id) {
        return findAllForAuthUser().stream().anyMatch(payment -> payment.getId().equals(id));
    }

    public Long resolveNextIdForUser() {
        Long lastId = paymentRepository.findMaxIdForUser(authUserService.getAuthenticatedUser());
        if (lastId == null) {
            return 1L;
        }
        return lastId + 1;
    }

    public Long countPaymentsByPaymentCategory(PaymentCategory paymentCategory) {
        return paymentRepository
            .countAllByUserAndPaymentCategory(authUserService.getAuthenticatedUser(),
                paymentCategory);
    }


}
