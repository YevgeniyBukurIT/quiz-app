package com.quizapp.core.interfaces.repository;

import com.quizapp.core.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test,Integer> {
    Optional<Test> getTestByAuthCode(String authCode);
}
