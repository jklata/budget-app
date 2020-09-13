package pl.jklata.budgetapp.domain;

import pl.jklata.budgetapp.domain.enums.EventLogRecordTypeEnum;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event_log_record")
public class EventLogRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    private String clazz;

    private LocalDateTime commitDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "record_type")
    private EventLogRecordTypeEnum recordType;

    @OneToMany
    private List<EventCommit> eventCommitList = new ArrayList<>();

}
