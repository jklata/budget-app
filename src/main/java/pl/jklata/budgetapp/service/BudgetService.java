package pl.jklata.budgetapp.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Budget;
import pl.jklata.budgetapp.repository.BudgetRepository;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public List<Budget> findAll() {
        return StreamSupport
                .stream(budgetRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Budget save(Budget budget) {

        if (!budgetRepository.findByName(budget.getName()).isPresent()) {
            return budgetRepository.save(budget);
        } else {
            throw new EntityExistsException();
        }
    }


}
