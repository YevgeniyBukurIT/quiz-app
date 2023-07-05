package com.quizapp.core.interfaces.services.domain;

import com.quizapp.core.models.AnswerOption;
import com.quizapp.core.models.Question;
import com.quizapp.core.models.Test;
import com.quizapp.core.models.user.AppUser;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public interface TestBuilderService {
    void addQuestion(int testId,Question question);


    void addAnswerOption(int questionId, AnswerOption answerOption);
    String commitTest(AppUser user, Test test);

    void removeUser(int testId,int userId);

    void saveTest(Test test);
}
