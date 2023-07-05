package com.quizapp.web.dto.test;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestOverviewDto extends TestDto {
    private int registered;
    private int completed;
    private String authCode;
}
