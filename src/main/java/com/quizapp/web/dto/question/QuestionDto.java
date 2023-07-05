package com.quizapp.web.dto.question;

import com.quizapp.web.dto.answer.options.AnswerOptionViewDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private int id;
    private String text;
    private double grade;
    private List<AnswerOptionViewDto> answerOptions;
}
