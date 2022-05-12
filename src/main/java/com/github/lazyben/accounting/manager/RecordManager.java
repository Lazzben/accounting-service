package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.model.common.Record;

public interface RecordManager {
    Record createRecord(Record record);
}
