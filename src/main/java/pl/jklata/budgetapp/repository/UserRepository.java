package pl.jklata.budgetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    @Query("SELECT u.avatar FROM User u WHERE u.login = ?1")
    byte[] getAvatarByUserLogin(String login);

}
