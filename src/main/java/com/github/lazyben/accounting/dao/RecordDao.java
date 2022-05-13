package com.github.lazyben.accounting.dao;

import com.github.lazyben.accounting.model.persistence.Record;

public interface RecordDao {
    void createRecord(Record record);

    Record getRecordByRecordId(Long recordId);

    void updateRecord(Record record);
}
