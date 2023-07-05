package com.quizapp.core.interfaces.services.domain;

import com.quizapp.core.models.QuestionStatistic;
import com.quizapp.core.models.Test;
import com.quizapp.web.dto.test.TestDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public interface StatisticService {
    List<QuestionStatistic> getTestStatistic(Test test);
    TestDto getTestWithStatistic(int testId);
}
