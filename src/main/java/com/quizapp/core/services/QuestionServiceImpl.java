package com.quizapp.core.services;

import com.quizapp.core.interfaces.repository.AnswerOptionRepository;
import com.quizapp.core.interfaces.repository.QuestionRepository;
import com.quizapp.core.interfaces.repository.UserRepository;
import com.quizapp.core.models.AnswerOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl  {
    private final AnswerOptionRepository answerOptionRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerOptionRepository answerOptionRepository, UserRepository userRepository) {
        this.answerOptionRepository = answerOptionRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }




}
