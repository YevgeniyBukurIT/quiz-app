package com.quizapp.core.interfaces.repository;

import com.quizapp.core.models.AnswerOption;
import com.quizapp.core.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Integer> {
}
