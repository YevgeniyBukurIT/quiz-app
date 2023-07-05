package com.quizapp.core.services;

import com.quizapp.core.interfaces.repository.TestRepository;
import com.quizapp.core.interfaces.services.domain.UserService;
import com.quizapp.core.models.Answer;
import com.quizapp.core.models.Test;
import com.quizapp.core.models.TestingSession;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final TestRepository testRepository;

    @Override
    public Collection<Test> getAvailableTests(int userId) {
        return testRepository.findAll();
    }

    @Override
    public Collection<Answer> getTestAnswers(int userId, int testId) {
        return null;
    }

    @Override
    public TestingSession getCurrentSession(int userId) {
        return null;
    }
}
