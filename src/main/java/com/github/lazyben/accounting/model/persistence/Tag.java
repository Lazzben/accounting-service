package com.github.lazyben.accounting.model.persistence;

import com.github.lazyben.accounting.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private Long id;
    private Long userId;
    private Status status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
