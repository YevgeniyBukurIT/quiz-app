package com.quizapp.web.dto.auth;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignInResponseDto {
    @Valid
    private UserDto user;
    private String token;
}
