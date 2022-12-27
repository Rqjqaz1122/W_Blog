package com.wfit.domain.vo;

import com.wfit.domain.dto.BlogDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo<T> {

    private List<T> list;

    private Long totalPage;


}
