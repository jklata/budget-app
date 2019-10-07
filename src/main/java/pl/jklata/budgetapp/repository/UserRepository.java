package pl.jklata.budgetapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
