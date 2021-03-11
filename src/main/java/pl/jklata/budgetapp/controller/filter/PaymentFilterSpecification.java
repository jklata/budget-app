package pl.jklata.budgetapp.controller.filter;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.Payment_;
import pl.jklata.budgetapp.dto.filter.PaymentFilterDto;

public class PaymentFilterSpecification implements Specification<Payment> {

    private PaymentFilterDto paymentFilterDto;

    public PaymentFilterSpecification(PaymentFilterDto paymentFilterDto) {
        this.paymentFilterDto = paymentFilterDto;
    }

    @Override
    public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> criteriaQuery,
        CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(paymentFilterDto.getId())){
            final Predicate predicate = criteriaBuilder.equal(root.get(Payment_.id), paymentFilterDto.getId());
            predicates.add(predicate);
        }

        if (!ObjectUtils.isEmpty(paymentFilterDto.getIdForUser())){
            final Predicate predicate = criteriaBuilder.equal(root.get(Payment_.idForUser), paymentFilterDto.getIdForUser());
            predicates.add(predicate);
        }

        if (!ObjectUtils.isEmpty(paymentFilterDto.getTitle())) {
            final Predicate predicate = criteriaBuilder.like(root.get(Payment_.title),
                paymentFilterDto.getTitle());
            predicates.add(predicate);
        }

        if (!ObjectUtils.isEmpty(paymentFilterDto.getPaymentCategory())){
            final Predicate predicate = criteriaBuilder.equal(root.get(Payment_.paymentCategory), paymentFilterDto.getPaymentCategory());
            predicates.add(predicate);
        }

        if (!ObjectUtils.isEmpty(paymentFilterDto.getPaymentType())){
            final Predicate predicate = criteriaBuilder.equal(root.get(Payment_.paymentType), paymentFilterDto.getPaymentType());
            predicates.add(predicate);
        }

        if (!ObjectUtils.isEmpty(paymentFilterDto.getUser())){
            final Predicate predicate = criteriaBuilder.equal(root.get(Payment_.user), paymentFilterDto.getUser());
            predicates.add(predicate);
        }

        //todo: Reszta predykatów

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
