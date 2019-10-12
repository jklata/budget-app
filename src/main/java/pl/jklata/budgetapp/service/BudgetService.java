package pl.jklata.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Budget;
import pl.jklata.budgetapp.repository.BudgetRepository;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BudgetService {

    BudgetRepository budgetRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Budget findByName(String name) {
        return budgetRepository.findByName(name).get();
    }

    public List<Budget> findAll() {
        return StreamSupport
                .stream(budgetRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Budget save(Budget transactionCategory) {

        if (!budgetRepository.findByName(transactionCategory.getName()).isPresent()) {
            return budgetRepository.save(transactionCategory);
        } else {
            throw new EntityExistsException();
        }
    }


}
