package pl.jklata.budgetapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.User;
import pl.jklata.budgetapp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final UserRepository userRepository;

    public User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = "";
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        }
        return userRepository.findByLogin(userName);
    }

}
