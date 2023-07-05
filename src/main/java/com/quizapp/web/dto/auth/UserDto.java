package com.quizapp.web.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UserDto {
    @Getter
    private int id;
    @Getter
    private String name;
}
