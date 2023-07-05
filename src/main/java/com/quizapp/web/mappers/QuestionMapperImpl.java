package com.quizapp.web.mappers;


import com.quizapp.core.interfaces.mappers.QuestionMapper;
import com.quizapp.core.models.Question;
import com.quizapp.web.dto.question.QuestionCreationDto;
import com.quizapp.web.dto.question.QuestionDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuestionMapperImpl implements QuestionMapper {
    private final AnswerOptionMapper answerOptionMapper;
    @Override
    public Question FromCreationDto(QuestionCreationDto creationDto) {
        return Question.builder()
                .Text(creationDto.getText())
                .maxPoints(creationDto.getMaxPoints())
                .answerOptions(creationDto
                        .getAnswerOptions().stream()
                        .map(answerOptionMapper::FromCreationDto).toList()
                )
                .build();
    }

    @Override
    public QuestionDto ToDefaultDto(Question question) {

        return new QuestionDto(question.getId(), question.getText(),question.getMaxPoints(),
                question.getAnswerOptions().stream().map(answerOptionMapper::ToViewDto).toList());
    }
}
