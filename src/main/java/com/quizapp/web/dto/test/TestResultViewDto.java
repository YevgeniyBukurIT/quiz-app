package com.quizapp.web.dto.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResultViewDto {
    private String username;
    private int userId;
    private double grade;
}
