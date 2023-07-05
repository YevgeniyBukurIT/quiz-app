package com.quizapp.core.interfaces.services;

import com.quizapp.core.models.user.AppUser;

public interface AuthenticationService {
    AppUser signUp(String firstname, String lastname, String email, String password);
    String signIn(String email,String password);
}
