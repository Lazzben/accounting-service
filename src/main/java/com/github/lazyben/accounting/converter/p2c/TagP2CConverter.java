package com.github.lazyben.accounting.converter.p2c;

import com.github.lazyben.accounting.model.persistence.Tag;
import com.google.common.base.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagP2CConverter extends Converter<Tag, com.github.lazyben.accounting.model.common.Tag> {
    @Override
    protected com.github.lazyben.accounting.model.common.Tag doForward(Tag tag) {
        return com.github.lazyben.accounting.model.common.Tag.builder()
                .userId(tag.getUserId())
                .id(tag.getId())
                .status(tag.getStatus())
                .description(tag.getDescription())
                .build();
    }

    @Override
    protected Tag doBackward(com.github.lazyben.accounting.model.common.Tag tag) {
        return null;
    }
}
