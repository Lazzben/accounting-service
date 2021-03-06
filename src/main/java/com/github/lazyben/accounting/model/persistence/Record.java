package com.github.lazyben.accounting.model.persistence;

import com.github.lazyben.accounting.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private String note;
    private Integer category;
    private List<Tag> tags;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

