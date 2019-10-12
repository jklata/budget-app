package pl.jklata.budgetapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.Hashtag;

@Repository
public interface HashtagRepository extends CrudRepository<Hashtag, Long> {
}
