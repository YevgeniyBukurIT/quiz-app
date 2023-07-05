package com.quizapp.web.dto.test;

import com.quizapp.web.dto.question.QuestionCreationDto;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public final class TestCreationDto {
    private String name;
    private String theme;
    private boolean graded;
    private boolean form;
    private List<QuestionCreationDto> questions;
}
