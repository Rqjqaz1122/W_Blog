package com.wfit.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VisitRecord {

    private Long id;

    private Integer pv;

    private Integer uv;

    private LocalDateTime date;

}
