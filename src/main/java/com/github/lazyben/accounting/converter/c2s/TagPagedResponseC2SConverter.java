package com.github.lazyben.accounting.converter.c2s;

import com.github.lazyben.accounting.model.PagedResponse;
import com.github.lazyben.accounting.model.common.Tag;
import com.google.common.base.Converter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TagPagedResponseC2SConverter extends Converter<PagedResponse<Tag>, PagedResponse<com.github.lazyben.accounting.model.service.Tag>> {
    private final TagC2SConverter tagC2SConverter;

    @Autowired
    public TagPagedResponseC2SConverter(TagC2SConverter tagC2SConverter) {
        this.tagC2SConverter = tagC2SConverter;
    }

    @Override
    protected PagedResponse<com.github.lazyben.accounting.model.service.Tag> doForward(PagedResponse<Tag> tagPagedResponse) {
        val data = tagPagedResponse.getData();
        val newData = new ArrayList<com.github.lazyben.accounting.model.service.Tag>();
        tagC2SConverter.convertAll(data).forEach(newData::add);
        return PagedResponse.<com.github.lazyben.accounting.model.service.Tag>builder()
                .hasNextPage(tagPagedResponse.isHasNextPage())
                .pageNum(tagPagedResponse.getPageNum())
                .totalPages(tagPagedResponse.getTotalPages())
                .pageSize(tagPagedResponse.getPageSize())
                .pageNum(tagPagedResponse.getPageNum())
                .totalElementSize(tagPagedResponse.getTotalElementSize())
                .data(newData)
                .build();
    }

    @Override
    protected PagedResponse<Tag> doBackward(PagedResponse<com.github.lazyben.accounting.model.service.Tag> tagPagedResponse) {
        throw new UnsupportedOperationException();
    }
}
