package com.quizapp.web.dto.question;

import com.quizapp.web.dto.answer.options.AnswerOptionCreationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QuestionCreationDto {
    private String text;

    private int maxPoints;

    private List<AnswerOptionCreationDto> answerOptions;

}
