package com.github.lazyben.accounting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    int totalPages;
    int totalElementSize;
    int pageNum;
    int pageSize;
    boolean hasNextPage;
    List<T> data;
}
