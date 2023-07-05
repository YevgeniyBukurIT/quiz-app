package com.quizapp.core.interfaces.services.domain;

import com.quizapp.core.models.Answer;
import com.quizapp.core.models.Test;
import com.quizapp.core.models.TestingSession;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface UserService {
    Collection<Test> getAvailableTests(int userId);
    Collection<Answer> getTestAnswers(int userId,int testId);

    TestingSession getCurrentSession(int userId);
}
