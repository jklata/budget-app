package pl.jklata.budgetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.Hashtag;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
