package com.quizapp.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
public class SignInRequestDto {
    @Getter
    @Email
    private String email;

    @Getter
    @NotEmpty
    @Length(min=3,max = 16)
    private String password;
}
