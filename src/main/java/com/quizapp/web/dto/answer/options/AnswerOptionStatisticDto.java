package com.quizapp.web.dto.answer.options;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerOptionStatisticDto extends AnswerOptionViewDto{
    private double percent;
    private boolean isTrue;
}
