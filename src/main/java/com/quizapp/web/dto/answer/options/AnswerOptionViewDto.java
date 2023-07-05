package com.quizapp.web.dto.answer.options;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AnswerOptionViewDto {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private boolean selected;
}
