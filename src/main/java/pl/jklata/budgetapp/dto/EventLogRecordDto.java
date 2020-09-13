package pl.jklata.budgetapp.dto;

import pl.jklata.budgetapp.domain.EventCommit;
import pl.jklata.budgetapp.domain.enums.EventLogRecordTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

public class EventLogRecordDto {

    private String author;
    private String clazz;
    private LocalDateTime commitDate;
    private EventLogRecordTypeEnum recordStatus;
    private List<EventCommit> eventCommitList;
}
