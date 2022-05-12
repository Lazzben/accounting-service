package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.converter.p2c.RecordP2CConverter;
import com.github.lazyben.accounting.dao.RecordDao;
import com.github.lazyben.accounting.dao.RecordTagMappingDao;
import com.github.lazyben.accounting.dao.TagDao;
import com.github.lazyben.accounting.exception.InvalidParameterException;
import com.github.lazyben.accounting.exception.ResourceNotFoundException;
import com.github.lazyben.accounting.model.common.Record;
import com.github.lazyben.accounting.model.common.Tag;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecordManagerImpl implements RecordManager {
    private final RecordDao recordDao;
    private final RecordTagMappingDao recordTagMappingDao;
    private final TagDao tagDao;
    private final RecordP2CConverter recordP2CConverter;

    @Autowired
    public RecordManagerImpl(RecordDao recordDao, RecordTagMappingDao recordTagMappingDao, TagDao tagDao, RecordP2CConverter recordP2CConverter) {
        this.recordDao = recordDao;
        this.recordTagMappingDao = recordTagMappingDao;
        this.tagDao = tagDao;
        this.recordP2CConverter = recordP2CConverter;
    }

    @Override
    public Record createRecord(Record record) {
        val newRecord = recordP2CConverter.reverse().convert(record);
        val tagsId = record.getTags().stream().map(Tag::getId).collect(Collectors.toList());
        val tags = tagDao.getTagListByTagIds(tagsId);
        assert newRecord != null;
        // 判断每一个tag是否合法，tag是否属于当前user
        checkTags(tags, record.getUserId());
        // create new record
        recordDao.createRecord(newRecord);
        recordTagMappingDao.batchInsertRecordTagMapping(tags, newRecord.getId());
        newRecord.setTags(tags);
        return recordP2CConverter.convert(newRecord);
    }

    @Override
    public Record getRecordByRecordId(Long recordId) {
        val record = Optional.ofNullable(recordDao.getRecordByRecordId(recordId))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("record %s is not found", recordId)));
        val tags = Optional.ofNullable(recordTagMappingDao.getTagsByRecordId(recordId))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("no tags matched for record %s", recordId)));
        record.setTags(tags);
        return recordP2CConverter.convert(record);
    }

    private void checkTags(List<com.github.lazyben.accounting.model.persistence.Tag> tags, Long userId) {
        if (tags == null) {
            throw new InvalidParameterException("Tags are not existed");
        }
        tags.forEach(tag -> {
            if (!tag.getUserId().equals(userId)) {
                throw new InvalidParameterException(String.format("Tag %s is not matched for user", tag.getUserId()));
            }
        });
    }
}
