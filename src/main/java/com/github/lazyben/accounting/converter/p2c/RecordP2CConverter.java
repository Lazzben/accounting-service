package com.github.lazyben.accounting.converter.p2c;

import com.github.lazyben.accounting.model.common.Tag;
import com.github.lazyben.accounting.model.persistence.Record;
import com.google.common.base.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecordP2CConverter extends Converter<Record, com.github.lazyben.accounting.model.common.Record> {
    private final TagP2CConverter tagP2CConverter;

    @Autowired
    public RecordP2CConverter(TagP2CConverter tagP2CConverter) {
        this.tagP2CConverter = tagP2CConverter;
    }

    @Override
    protected com.github.lazyben.accounting.model.common.Record doForward(Record record) {
        List<Tag> tags = new ArrayList<>();
        tagP2CConverter.convertAll(record.getTags()).forEach(tags::add);

        return com.github.lazyben.accounting.model.common.Record.builder()
                .id(record.getId())
                .userId(record.getUserId())
                .note(record.getNote())
                .amount(record.getAmount())
                .status(record.getStatus())
                .category(record.getCategory())
                .tags(tags)
                .build();
    }

    @Override
    protected Record doBackward(com.github.lazyben.accounting.model.common.Record record) {
        List<com.github.lazyben.accounting.model.persistence.Tag> tags = new ArrayList<>();
        tagP2CConverter.reverse().convertAll(record.getTags()).forEach(tags::add);

        return Record.builder()
                .id(record.getId())
                .userId(record.getUserId())
                .amount(record.getAmount())
                .category(record.getCategory())
                .note(record.getNote())
                .status(record.getStatus())
                .tags(tags)
                .createTime(null)
                .updateTime(null)
                .build();
    }
}
