package com.quizapp.web.dto.test;

import com.quizapp.web.dto.question.QuestionDto;
import com.quizapp.web.dto.answer.options.AnswerOptionResultDto;
import com.quizapp.web.dto.question.QuestionStatisticDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class TestDto {
    private int id;
    private String author;
    private long duration;
    private double grade;
    private boolean isGraded;
    private boolean isForm;
    private String name;
    private String theme;
    private int userId;
    private List<QuestionDto> questions;
    private List<AnswerOptionResultDto> answers;
    private List<QuestionStatisticDto> statistic;
}
