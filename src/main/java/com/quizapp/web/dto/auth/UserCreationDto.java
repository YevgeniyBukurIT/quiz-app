package com.quizapp.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
public class UserCreationDto {
    private String email;

    @NotEmpty
    @Length(max = 16)
    private String Name;


    private String Password;
}
