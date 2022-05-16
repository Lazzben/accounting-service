package com.github.lazyben.accounting.converter.c2s;

import com.github.lazyben.accounting.model.PagedResponse;
import com.github.lazyben.accounting.model.common.Record;
import com.google.common.base.Converter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RecordPagedResponseC2SConverter extends Converter<PagedResponse<Record>, PagedResponse<com.github.lazyben.accounting.model.service.Record>> {
    private final RecordC2SConverter recordC2SConverter;

    @Autowired
    public RecordPagedResponseC2SConverter(RecordC2SConverter recordC2SConverter) {
        this.recordC2SConverter = recordC2SConverter;
    }

    @Override
    protected PagedResponse<com.github.lazyben.accounting.model.service.Record> doForward(PagedResponse<Record> recordPagedResponse) {
        val records = recordPagedResponse.getData();
        val newData = new ArrayList<com.github.lazyben.accounting.model.service.Record>();
        recordC2SConverter.convertAll(records).forEach(newData::add);
        return PagedResponse.<com.github.lazyben.accounting.model.service.Record>builder()
                .totalElementSize(recordPagedResponse.getTotalElementSize())
                .pageNum(recordPagedResponse.getPageNum())
                .pageSize(recordPagedResponse.getPageSize())
                .hasNextPage(recordPagedResponse.isHasNextPage())
                .data(newData)
                .build();
    }

    @Override
    protected PagedResponse<Record> doBackward(PagedResponse<com.github.lazyben.accounting.model.service.Record> recordPagedResponse) {
        return null;
    }
}
