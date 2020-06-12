package pl.jklata.budgetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.UserRole;
import pl.jklata.budgetapp.domain.enums.Role;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByRole(Role role);

}
