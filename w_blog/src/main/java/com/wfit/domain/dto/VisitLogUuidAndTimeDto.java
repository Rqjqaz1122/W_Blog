package com.wfit.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitLogUuidAndTimeDto {

    private String uuid;

    private LocalDateTime createTime;

    private Long count;
}
