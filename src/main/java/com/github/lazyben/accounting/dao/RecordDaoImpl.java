package com.github.lazyben.accounting.dao;

import com.github.lazyben.accounting.dao.mapper.RecordMapper;
import com.github.lazyben.accounting.model.persistence.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

}
