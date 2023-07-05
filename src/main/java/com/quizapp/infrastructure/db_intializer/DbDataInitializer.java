package com.quizapp.infrastructure.db_intializer;

import com.quizapp.core.interfaces.repository.AnswerOptionRepository;
import com.quizapp.core.interfaces.repository.QuestionRepository;
import com.quizapp.core.interfaces.repository.TestRepository;
import com.quizapp.core.models.AnswerOption;
import com.quizapp.core.models.Question;
import com.quizapp.core.models.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Component
public class DbDataInitializer implements CommandLineRunner {
    private final AnswerOptionRepository answerOptionRepository;
    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    @Autowired
    public DbDataInitializer(QuestionRepository questionRepository, AnswerOptionRepository answerOptionRepository, TestRepository testRepository) {
        this.answerOptionRepository = answerOptionRepository;
        this.questionRepository = questionRepository;
        this.testRepository = testRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (testRepository.findAll().stream().anyMatch(test -> true)){
            return;
        }

        var test = Test.builder()
                .authCode("cringe")
                .theme("Крінжа")
                .name("Українська крінжа")
                .questions(new ArrayList<>())
                .build();

        test = testRepository.save(test);

        var question = new Question("Пес патрон?",5);
        question.setTest(test);
        test.addQuestion(question);

        var answerOption1 = AnswerOption.builder()
                .question(question)
                .text("База")
                .pointsIfCorrect(5)
                .build();
        var answerOption2 = AnswerOption.builder()
                .question(question)
                .text("Крінж")
                .pointsIfCorrect(0)
                .build();
        question.addAnswerToPoll(answerOption1);
        question.addAnswerToPoll(answerOption2);

        var question1 = new Question("Повітряна тривога?",5);
        question1.setTest(test);
        test.addQuestion(question1);
        var answerOption3 = AnswerOption.builder()
                .question(question1)
                .text("База")
                .pointsIfCorrect(5)
                .build();
        var answerOption4 = AnswerOption.builder()
                .question(question1)
                .text("Крінж")
                .pointsIfCorrect(0)
                .build();
        question1.addAnswerToPoll(answerOption3);
        question1.addAnswerToPoll(answerOption4);

        testRepository.saveAndFlush(test);
        var s = testRepository.getById(1);
    }
}
