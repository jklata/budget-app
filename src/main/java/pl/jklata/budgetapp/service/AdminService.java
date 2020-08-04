package pl.jklata.budgetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.repository.UserRepository;

/**
 * @author Jakub Klata, Pentacomp Systemy Informatyczne S.A.
 */
@Service
@Secured("ROLE_ADMIN")
public class AdminService {

    private UserRepository userRepository;

    @Autowired
    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> findPaginated(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
