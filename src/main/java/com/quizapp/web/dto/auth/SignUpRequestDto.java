package com.quizapp.web.dto.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public class SignUpRequestDto {
    @NotEmpty
    @Getter
    private String firstname;

    @Getter
    @NotEmpty
    private String lastname;

    @Getter
    @Email
    private String email;

    @Getter
    @NotEmpty
    @Length(min=3,max = 16)
    private String password;
}
