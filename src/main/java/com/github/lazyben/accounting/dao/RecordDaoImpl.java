package com.github.lazyben.accounting.dao;

import com.github.lazyben.accounting.dao.mapper.RecordMapper;
import com.github.lazyben.accounting.model.persistence.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class RecordDaoImpl implements RecordDao {
    private final RecordMapper recordMapper;

    @Autowired
    public RecordDaoImpl(RecordMapper recordMapper) {
        this.recordMapper = recordMapper;
    }

    @Override
    public void createRecord(Record record) {
        record.setStatus(1);
        record.setCreateTime(LocalDateTime.now());
        recordMapper.createRecord(record);
        log.debug("create Record {}", record);
    }

    @Override
    public Record getRecordByRecordId(Long recordId) {
        return recordMapper.getRecordByRecordId(recordId);
    }

    @Override
    public void updateRecord(Record record) {
        recordMapper.updateRecord(record);
    }

    @Override
    public int getRecordsCount(Long id) {
        return recordMapper.getRecordCount(id);
    }

    @Override
    public List<Record> getRecords(Long id, int offset, int pageSize) {
        return recordMapper.getRecords(id, offset, pageSize);
    }

}
