package com.github.lazyben.accounting.controller;

import com.github.lazyben.accounting.converter.c2s.RecordC2SConverter;
import com.github.lazyben.accounting.exception.InvalidParameterException;
import com.github.lazyben.accounting.manager.RecordManager;
import com.github.lazyben.accounting.model.service.Record;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1.0/record")
public class RecordController {
    private final RecordManager recordManager;
    private final RecordC2SConverter recordC2SConverter;

    @Autowired
    public RecordController(RecordManager recordManager, RecordC2SConverter recordC2SConverter) {
        this.recordManager = recordManager;
        this.recordC2SConverter = recordC2SConverter;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public Record createRecord(@RequestBody Record record) {
        checkRecord(record);
        val recordCommon = recordC2SConverter.reverse().convert(record);
        return recordC2SConverter.convert(recordManager.createRecord(recordCommon));
    }

    @GetMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public Record getRecordByRecordId(@PathVariable("id") Long recordId) {
        return null;
    }

    @PutMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public Record updateRecord(@PathVariable("id") Long recordId, @RequestBody Record record) {
        return null;
    }

    private void checkRecord(Record record) {
        if (record.getAmount() == null || record.getAmount().doubleValue() <= 0) {
            throw new InvalidParameterException("Mount is null or invalid");
        }
        if (record.getCategory() == null ||
                !(record.getCategory().equals("outcome") || record.getCategory().equals("income"))) {
            throw new InvalidParameterException("Category is null or invalid");
        }
        if (record.getUserId() == null || record.getUserId() <= 0) {
            throw new InvalidParameterException("User id is null or invalid");
        }
        if (record.getTags() == null) {
            throw new InvalidParameterException("Tag is null");
        }
    }
}
