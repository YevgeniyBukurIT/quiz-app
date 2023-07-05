package com.quizapp.core.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class AnswerOptionStatistic {

    private AnswerOption answerOption;
    private double selectedPercent;
    private boolean isTrue;

}
