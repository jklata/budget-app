package pl.jklata.budgetapp.service;

import org.springframework.stereotype.Service;
import pl.jklata.budgetapp.domain.Payment;
import pl.jklata.budgetapp.domain.enums.EventLogRecordTypeEnum;
import pl.jklata.budgetapp.dto.PaymentDto;

import java.util.List;

@Service
public class EventLogService {

    private PaymentDto beforeStateObject;
    private Payment afterStateObject;

    public void preserveBeforeState(List<Object> annotatedMethodParameters) {
        Object firstArgument = annotatedMethodParameters.get(0);
        if (firstArgument instanceof PaymentDto) {
            this.beforeStateObject = (PaymentDto) firstArgument;
        }
    }

    public void preserveAfterState(Object obj) {
        if (obj instanceof Payment) {
            this.afterStateObject = (Payment) obj;
        }
    }

    public void processEventLog() {
        EventLogRecordTypeEnum recordType = resolveEventLogRecordType();

    }

    private EventLogRecordTypeEnum resolveEventLogRecordType() {
        // NEW
        if (beforeStateObject.getId() == null && beforeStateObject.getInsertDate() == null) {
            return EventLogRecordTypeEnum.NEW;
        }

        // UPDATED
        if (beforeStateObject.getId() != null && beforeStateObject.getInsertDate() != null
                && beforeStateObject.getId().equals(afterStateObject.getId())) {
            return EventLogRecordTypeEnum.UPDATED;
        }

        // todo: DELETED
        else return null;
    }


    public void prepareLogRecord() {

    }
}
