package com.quizapp.web.mappers;

import com.quizapp.core.models.AnswerOption;
import com.quizapp.web.dto.answer.options.AnswerOptionCreationDto;
import com.quizapp.web.dto.answer.options.AnswerOptionViewDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AnswerOptionMapper implements com.quizapp.core.interfaces.mappers.AnswerOptionMapper {

    public AnswerOption FromCreationDto(AnswerOptionCreationDto answerOptionDto){
        return AnswerOption.builder()
                .text(answerOptionDto.getText())
                .pointsIfCorrect(answerOptionDto.getPoints()).build();
    }

    @Override
    public AnswerOptionViewDto ToViewDto(AnswerOption answerOption) {
        return new AnswerOptionViewDto(answerOption.getId(), answerOption.getText(),false);
    }
}
