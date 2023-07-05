package com.quizapp.core.interfaces.mappers;

import com.quizapp.core.models.AnswerOption;
import com.quizapp.web.dto.answer.options.AnswerOptionCreationDto;
import com.quizapp.web.dto.answer.options.AnswerOptionViewDto;

public interface AnswerOptionMapper extends Mapper<AnswerOption>{
    AnswerOption FromCreationDto(AnswerOptionCreationDto creationDto);

    AnswerOptionViewDto ToViewDto(AnswerOption answerOption);
}
