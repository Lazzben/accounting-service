package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.converter.p2c.RecordP2CConverter;
import com.github.lazyben.accounting.dao.RecordDao;
import com.github.lazyben.accounting.dao.RecordTagMappingDao;
import com.github.lazyben.accounting.dao.TagDao;
import com.github.lazyben.accounting.exception.InvalidParameterException;
import com.github.lazyben.accounting.exception.ResourceNotFoundException;
import com.github.lazyben.accounting.model.PagedResponse;
import com.github.lazyben.accounting.model.common.Record;
import com.github.lazyben.accounting.model.common.Tag;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public Record updateRecord(Long recordId, Record record) {
        // 判断recordId对应的record是否存在，并获取老的record。
        val oldRecord = recordP2CConverter.reverse().convert(getRecordByRecordId(recordId));
        val recordPersistence = recordP2CConverter.reverse().convert(record);
        assert recordPersistence != null;
        assert oldRecord != null;

        // 如果record内的tags存在，判断其是否合。
        // 如果合法，更新tag。
        checkTagsAndUpdateRecordsTags(recordPersistence, oldRecord);

        // 更新record
        recordDao.updateRecord(recordPersistence);

        // 构造更新后的record，并返回
        val newRecord = recordDao.getRecordByRecordId(recordId);
        val tags = recordTagMappingDao.getTagsByRecordId(newRecord.getId());
        newRecord.setTags(tags);
        return recordP2CConverter.convert(newRecord);
    }

    @Override
    public PagedResponse<Record> getRecords(Long userId, int pageNum, int pageSize) {
        val count = recordDao.getRecordsCount(userId);
        val biggestPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        if (pageNum > biggestPageNum) {
            throw new InvalidParameterException(
                    String.format("Max pageNum is %s, which is smaller than %s.Total tags %s",
                            biggestPageNum, pageNum, count));
        }
        int offset = (pageNum - 1) * pageSize;
        val recordsPersistence = recordDao.getRecords(userId, offset, pageSize);
        recordsPersistence.forEach((record) -> {
            val tags = Optional.ofNullable(recordTagMappingDao.getTagsByRecordId(record.getId()))
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("no tags matched for record %s", record.getId())));
            record.setTags(tagDao.getTagListByTagIds(getTagsIds(tags)));
        });
        val recordsCommon = new ArrayList<Record>();
        recordP2CConverter.convertAll(recordsPersistence).forEach(recordsCommon::add);
        return PagedResponse.<Record>builder()
                .data(recordsCommon)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .totalPages(biggestPageNum)
                .totalElementSize(count)
                .hasNextPage(pageNum != biggestPageNum)
                .build();

    }

    private void checkTagsAndUpdateRecordsTags(com.github.lazyben.accounting.model.persistence.Record recordToBeUpdated,
                                               com.github.lazyben.accounting.model.persistence.Record oldRecord) {
        // 如果tags为空则说明无需更新
        if (recordToBeUpdated.getTags() == null) {
            return;
        }
        // 判断用户传入的 tag id 是否都能找到对应的id
        val tagsToBeUpdatedIds = getTagsIds(recordToBeUpdated.getTags());
        val tagsToBeUpdated = tagDao.getTagListByTagIds(tagsToBeUpdatedIds);
        val realTagsToBeUpdatedIds = getTagsIds(tagsToBeUpdated);
        if (!tagsToBeUpdatedIds.equals(realTagsToBeUpdatedIds)) {
            throw new InvalidParameterException("some tags do not exits");
        }
        recordToBeUpdated.setTags(tagsToBeUpdated);

        // 原tags如果等于tags，则说明无需修改
        // 否则执行更新操作
        val olgTagIds = getTagsIds(oldRecord.getTags());
        if (!realTagsToBeUpdatedIds.equals(olgTagIds)) {
            checkTags(tagsToBeUpdated, oldRecord.getUserId());
            recordTagMappingDao.batchDeleteRecordTagMapping(oldRecord.getId());
            recordTagMappingDao.batchInsertRecordTagMapping(recordToBeUpdated.getTags(), oldRecord.getUserId());
        } else {
            recordToBeUpdated.setTags(null);
        }
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

    private List<Long> getTagsIds(List<com.github.lazyben.accounting.model.persistence.Tag> tags) {
        return tags.stream()
                .map(com.github.lazyben.accounting.model.persistence.Tag::getId)
                .sorted()
                .collect(Collectors.toList());
    }
}
