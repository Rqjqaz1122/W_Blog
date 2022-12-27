package com.wfit.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBlogVo {

    private Long id;

    private String title;
//    private String password;

    private Boolean privacy;

}
