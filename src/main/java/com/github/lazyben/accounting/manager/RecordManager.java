package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.model.common.Record;

public interface RecordManager {
    Record createRecord(Record record);

    Record getRecordByRecordId(Long recordId);

    Record updateRecord(Long recordId, Record record);
}
