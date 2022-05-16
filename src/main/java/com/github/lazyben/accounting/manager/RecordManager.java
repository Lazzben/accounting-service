package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.model.PagedResponse;
import com.github.lazyben.accounting.model.common.Record;

public interface RecordManager {
    Record createRecord(Record record);

    Record getRecordByRecordId(Long recordId);

    Record updateRecord(Long recordId, Record record);

    PagedResponse<Record> getRecords(Long id, int pageNum, int pageSize);
}
