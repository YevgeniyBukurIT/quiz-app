package com.quizapp.web.dto.question;

import com.quizapp.web.dto.answer.options.AnswerOptionStatisticDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class QuestionStatisticDto {
    private List<AnswerOptionStatisticDto> answerStatistic;
}
