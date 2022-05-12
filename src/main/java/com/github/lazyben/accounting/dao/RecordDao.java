package com.github.lazyben.accounting.dao;

import com.github.lazyben.accounting.model.persistence.Record;

public interface RecordDao {
    void createRecord(Record record);
}
