package com.github.lazyben.accounting.model.service;

import com.github.lazyben.accounting.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private Long id;
    private Long userId;
    private Status status;
    private String description;
}
