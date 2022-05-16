package com.github.lazyben.accounting.dao;

import com.github.lazyben.accounting.model.persistence.Record;

import java.util.List;

public interface RecordDao {
    void createRecord(Record record);

    Record getRecordByRecordId(Long recordId);

    void updateRecord(Record record);

    int getRecordsCount(Long id);

    List<Record> getRecords(Long id, int offset, int pageSize);
}
