package com.github.lazyben.accounting.converter.c2s;

import com.github.lazyben.accounting.model.common.Record;
import com.github.lazyben.accounting.model.service.Tag;
import com.google.common.base.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecordC2SConverter extends Converter<Record, com.github.lazyben.accounting.model.service.Record> {

    private final TagC2SConverter tagC2SConverter;

    @Autowired
    public RecordC2SConverter(TagC2SConverter tagC2SConverter) {
        this.tagC2SConverter = tagC2SConverter;
    }

    @Override
    protected com.github.lazyben.accounting.model.service.Record doForward(Record record) {
        List<Tag> tags = new ArrayList<>();
        tagC2SConverter.convertAll(record.getTags()).forEach(tags::add);

        return com.github.lazyben.accounting.model.service.Record.builder()
                .id(record.getId())
                .userId(record.getUserId())
                .amount(record.getAmount())
                .category(record.getCategory() == 0 ? "outcome" : "income")
                .note(record.getNote())
                .tags(tags)
                .build();
    }

    @Override
    protected Record doBackward(com.github.lazyben.accounting.model.service.Record record) {
        List<com.github.lazyben.accounting.model.common.Tag> tags = new ArrayList<>();
        tagC2SConverter.reverse().convertAll(record.getTags()).forEach(tags::add);

        return Record.builder()
                .id(record.getId())
                .userId(record.getUserId())
                .amount(record.getAmount())
                .category("outcome".equals(record.getCategory()) ? 0 : 1)
                .note(record.getNote())
                .tags(tags)
                .build();
    }
}
