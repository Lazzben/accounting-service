package com.github.lazyben.accounting.dao;

import com.github.lazyben.accounting.dao.mapper.RecordTagMappingMapper;
import com.github.lazyben.accounting.model.persistence.RecordTagMapping;
import com.github.lazyben.accounting.model.persistence.Tag;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordTagMappingDaoImpl implements RecordTagMappingDao {
    private final RecordTagMappingMapper recordTagMappingMapper;

    @Autowired
    public RecordTagMappingDaoImpl(RecordTagMappingMapper recordTagMappingMapper) {
        this.recordTagMappingMapper = recordTagMappingMapper;
    }

    @Override
    public void batchInsertRecordTagMapping(List<Tag> tags, Long recordId) {
        final LocalDateTime now = LocalDateTime.now();
        val recordTagMappings = tags.stream()
                .map(tag -> RecordTagMapping.builder()
                        .recordId(recordId)
                        .status(1)
                        .tagId(tag.getId())
                        .createTime(now)
                        .build())
                .collect(Collectors.toList());

        recordTagMappingMapper.batchInsertRecordTagMapping(recordTagMappings);
    }
}
