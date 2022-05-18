package com.github.lazyben.accounting.model.common;

import com.github.lazyben.accounting.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {
    private Long id;
    private Long userId;
    private Status status;
    private String description;
}
