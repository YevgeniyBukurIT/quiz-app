package com.quizapp.core.services;

import com.quizapp.core.interfaces.repository.AnswerOptionRepository;
import com.quizapp.core.interfaces.repository.QuestionRepository;
import com.quizapp.core.interfaces.repository.UserRepository;
import com.quizapp.core.interfaces.services.domain.TestBuilderService;
import com.quizapp.core.interfaces.repository.TestRepository;
import com.quizapp.core.models.AnswerOption;
import com.quizapp.core.models.Question;
import com.quizapp.core.models.Test;
import com.quizapp.core.models.TestState;
import com.quizapp.core.models.user.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class TestBuilderServiceImpl implements TestBuilderService {

    private final QuestionRepository questionRepository;

    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final AnswerOptionRepository answerOptionRepository;

    @Override
    public void addQuestion(int testId, Question question) {
        var test = testRepository.findById(testId).orElseThrow(() -> new IllegalArgumentException("Test not found"));

        test.addQuestion(question);
    }

    public void addAnswerOption(int questionId, AnswerOption answerOption){
        var question = questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Question not found."));

        question.addAnswerToPoll(answerOption);
    }

    public String commitTest(AppUser user,Test test){

        if (test.getQuestions().isEmpty())
            throw new IllegalArgumentException("Error");

        if (test.getTheme().isBlank() || test.getTheme().isEmpty())
            throw new IllegalArgumentException("Error");

        var questions = test.getQuestions();
        test.setQuestions(new ArrayList<>());

        user.getCreatedTests().add(test);
        test.setCreator(user);


        questions.forEach(q -> {
            if (q.getMaxPoints() == 0)
                throw new IllegalArgumentException();
            if (q.getAnswerOptions().isEmpty())
                throw new IllegalArgumentException("Error");
            if (q.getText().isBlank() || q.getText().isEmpty())
                throw new IllegalArgumentException("Error");
            q.getAnswerOptions().forEach(ao -> {
                if (ao.getText().isEmpty() || ao.getText().isEmpty())
                    throw new IllegalArgumentException("Error");
            });
        });

        testRepository.saveAndFlush(test);

        questions.forEach(q -> {
            var answerOptions = q.getAnswerOptions();
            q.setAnswerOptions(new ArrayList<>());
            questionRepository.saveAndFlush(q);
            answerOptions.forEach(ao -> {
                q.addAnswerToPoll(ao);
                answerOptionRepository.saveAndFlush(ao);
            });
            test.addQuestion(q);
            questionRepository.saveAndFlush(q);
        });

        testRepository.saveAndFlush(test);
        return test.getAuthCode();
    }

    @Override
    public void removeUser(int testId, int userId) {

    }

    @Override
    public void saveTest(Test test) {

    }
}
