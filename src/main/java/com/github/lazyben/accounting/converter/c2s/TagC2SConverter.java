package com.github.lazyben.accounting.converter.c2s;

import com.github.lazyben.accounting.model.common.Tag;
import com.google.common.base.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagC2SConverter extends Converter<Tag, com.github.lazyben.accounting.model.service.Tag> {
    @Override
    protected com.github.lazyben.accounting.model.service.Tag doForward(Tag tag) {
        return com.github.lazyben.accounting.model.service.Tag
                .builder()
                .id(tag.getId())
                .description(tag.getDescription())
                .status(tag.getStatus())
                .userId(tag.getUserId())
                .build();
    }

    @Override
    protected Tag doBackward(com.github.lazyben.accounting.model.service.Tag tag) {
        return null;
    }
}
