package com.quizapp.core.interfaces.repository;

import com.quizapp.core.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    Optional<List<Answer>> getAnswersByUserId(int userId);

}
