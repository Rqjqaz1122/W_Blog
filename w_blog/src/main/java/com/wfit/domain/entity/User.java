package com.wfit.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String email;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String role;

}
