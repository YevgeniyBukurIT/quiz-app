package com.quizapp.core.interfaces.mappers;

import com.quizapp.core.models.Question;
import com.quizapp.web.dto.question.QuestionCreationDto;
import com.quizapp.web.dto.question.QuestionDto;

public interface QuestionMapper extends Mapper<Question> {
    Question FromCreationDto(QuestionCreationDto creationDto);

    QuestionDto ToDefaultDto(Question question);

}
