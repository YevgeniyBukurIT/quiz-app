package com.quizapp.core.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionStatistic {

    @Getter
    @Setter
    public Question question;

    @Setter
    @Getter
    public List<AnswerOptionStatistic> answerOptionsStatistic;

}
