package com.quizapp.core.interfaces.repository;

import com.quizapp.core.models.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,Integer> {
    Optional<AppUser> findByEmail(String email);
}
