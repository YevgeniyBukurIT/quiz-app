package com.quizapp.core.interfaces.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthMethodService {
    String generateToken(UserDetails userDetails);

}
