package com.quizapp.core.interfaces.services.domain;


import com.quizapp.web.dto.test.TestDto;
import com.quizapp.web.dto.test.TestOverviewDto;
import com.quizapp.web.dto.test.TestViewDto;
import org.springframework.stereotype.Service;


@Service
public interface TestingService {
    TestViewDto getUsersResults(int testId);
    TestDto enterTest(int userId,int testId);

    TestOverviewDto getOverview(int testId);
    TestDto getUserResults(int userId, int testId);
    void ensureUserParticipation(int userId,int testId);
    void answerQuestion(int userId,int questionId,int answerOptionId);
    void endTest(int userId,int testId);
}
