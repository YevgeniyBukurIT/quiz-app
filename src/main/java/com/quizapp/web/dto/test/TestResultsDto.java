package com.quizapp.web.dto.test;

import com.quizapp.web.dto.question.QuestionResultDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TestResultsDto {
    private String name;
    private String theme;
    private List<QuestionResultDto> questions;
}
