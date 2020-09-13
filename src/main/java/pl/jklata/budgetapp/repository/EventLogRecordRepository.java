package pl.jklata.budgetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jklata.budgetapp.domain.EventLogRecord;

@Repository
public interface EventLogRecordRepository extends JpaRepository<EventLogRecord, Long> {
}
