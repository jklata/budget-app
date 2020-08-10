package pl.jklata.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.converter.PaymentDtoToEntity;
import pl.jklata.budgetapp.converter.PaymentEntityToDto;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.domain.enums.PaymentType;
import pl.jklata.budgetapp.dto.PaymentDto;
import pl.jklata.budgetapp.repository.PaymentRepository;
import pl.jklata.budgetapp.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private UserRepository userRepository;
    private PaymentDtoToEntity paymentDtoToEntity;
    private PaymentEntityToDto paymentEntityToDto;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository, PaymentDtoToEntity paymentDtoToEntity, PaymentEntityToDto paymentEntityToDto) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.paymentDtoToEntity = paymentDtoToEntity;
        this.paymentEntityToDto = paymentEntityToDto;
    }


    public Page<PaymentDto> findPaginated(Pageable pageable) {
        return paymentRepository.findAllByUser(getAuthenticatedUser(), pageable).map(payment -> paymentEntityToDto.convert(payment));
    }

    public Payment save(PaymentDto paymentDto) {
        Payment payment = paymentDtoToEntity.convert(paymentDto);
        payment.setInsertDate(LocalDate.now());
        payment.setUser(getAuthenticatedUser());
        if (payment.getId() == null) {
            payment.setIdForUser(resolveNextIdForUser());
        }
        Payment persistedPayment = paymentRepository.save(payment);
        return persistedPayment;
    }

    public Payment saveDataInitializer(Payment payment) {
        payment.setInsertDate(LocalDate.now());
        if (payment.getPaymentType() == PaymentType.EXPENSE && (payment.getAmount().signum() == 1)) {
            payment.setAmount(payment.getAmount().negate());
        }
        return paymentRepository.save(payment);
    }

    public List<PaymentDto> findAllForAuthUser() {
        return paymentRepository.findAllByUser(getAuthenticatedUser()).stream()
                .map(payment -> paymentEntityToDto.convert(payment))
                .collect(Collectors.toList());
    }

    public List<PaymentDto> findAllForAuthUserAndYearAndMonth(int year, int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, startDate.lengthOfMonth());

        return paymentRepository.findAllByUserAndPaymentDateBetween(getAuthenticatedUser(), startDate, endDate).stream()
                .map(payment -> paymentEntityToDto.convert(payment))
                .collect(Collectors.toList());
    }

    public PaymentDto findByIdForAuthUser(Long id) {
        Payment payment = paymentRepository.findByIdAndUser(id, getAuthenticatedUser()).orElseThrow(NoSuchElementException::new);
        return paymentEntityToDto.convert(payment);
    }

    public void deleteById(Long id) {
        if (isUserOwnsPayment(id)) {
            paymentRepository.deleteById(id);
        }
    }

    protected User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = "";
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        }
        return userRepository.findByLogin(userName);
    }

    private boolean isUserOwnsPayment(Long id) {
        return findAllForAuthUser().stream().anyMatch(payment -> payment.getId().equals(id));
    }

    public Long resolveNextIdForUser() {
        Long lastId = paymentRepository.findMaxIdForUser(getAuthenticatedUser());
        if (lastId == null) {
            return 1L;
        }
        return lastId + 1;
    }

    public List<Integer> getDistinctYearFromAllPayments() {
        return paymentRepository.getDistinctYearFromAllPayments(getAuthenticatedUser());
    }

}
