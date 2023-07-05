package com.quizapp.web.dto.answer.options;


import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
public class AnswerOptionCreationDto {
    private String text;
    private double points;
}
