package com.quizapp.core.services;

import com.quizapp.core.interfaces.mappers.TestMapper;
import com.quizapp.core.interfaces.repository.AnswerRepository;
import com.quizapp.core.interfaces.repository.QuestionRepository;
import com.quizapp.core.interfaces.repository.TestRepository;
import com.quizapp.core.interfaces.repository.TestingSessionRepository;
import com.quizapp.core.interfaces.services.domain.StatisticService;
import com.quizapp.core.models.AnswerOptionStatistic;
import com.quizapp.core.models.Question;
import com.quizapp.core.models.QuestionStatistic;
import com.quizapp.core.models.Test;
import com.quizapp.web.dto.answer.options.AnswerOptionStatisticDto;
import com.quizapp.web.dto.question.QuestionStatisticDto;
import com.quizapp.web.dto.test.TestDto;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {


    private final TestingSessionRepository sessionRepository;
    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;
    private final TestRepository testRepository;
    private final TestMapper testMapper;

    @Override
    public List<QuestionStatistic> getTestStatistic(Test test) {
        var answers = test.getAnswers();
        var questions = test.getQuestions();

        return questions.stream().map(q -> {
            var stat = new QuestionStatistic();
            stat.setQuestion(q);
            stat.setAnswerOptionsStatistic(new ArrayList<>());
             q.getAnswerOptions()
                     .forEach(ao ->
                         stat.getAnswerOptionsStatistic()
                                 .add(new AnswerOptionStatistic(ao,ao.getAnswers().size(),ao.getPointsIfCorrect()>0))
                     );
             double totalSelected = stat.getAnswerOptionsStatistic().stream().map(AnswerOptionStatistic::getSelectedPercent).reduce(Double::sum).get();
             stat.getAnswerOptionsStatistic().forEach(aos -> {
                 aos.setSelectedPercent((aos.getSelectedPercent()/totalSelected)*100);
             });
            return stat;
        }).toList();
    }

    @Override
    public TestDto getTestWithStatistic(int testId) {
        var test = testRepository.findById(testId).orElseThrow(() -> new IllegalArgumentException("Test not found."));
        var statistic = getTestStatistic(test);

        var testDto = testMapper.ToSessionDto(test);
        testDto.setStatistic(statistic.stream().map(stat ->
            new QuestionStatisticDto(stat.getAnswerOptionsStatistic().stream()
                    .map(aoStat -> {
                        var aodto = new AnswerOptionStatisticDto(aoStat.getSelectedPercent(),aoStat.isTrue());
                        aodto.setText(aoStat.getAnswerOption().getText());
                        aodto.setId(aoStat.getAnswerOption().getId());

                        return aodto;
                    }).toList())
        ).toList());
        return testDto;
    }
}
