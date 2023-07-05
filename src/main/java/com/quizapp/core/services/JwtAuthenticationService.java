package com.quizapp.core.services;

import com.quizapp.core.interfaces.repository.UserRepository;
import com.quizapp.core.interfaces.services.AuthMethodService;
import com.quizapp.core.interfaces.services.AuthenticationService;
import com.quizapp.core.models.user.AppUser;
import com.quizapp.core.models.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationService implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthMethodService authMethodService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public JwtAuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthMethodService authMethodService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authMethodService = authMethodService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public AppUser signUp(String firstname, String lastname, String email, String password) {
        var user = AppUser.builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();
        userRepository.saveAndFlush(user);
        return user;
    }

    @Override
    public String signIn(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password));
        var usr = userRepository.findByEmail(email).orElseThrow();
        return authMethodService.generateToken(usr);
    }
}
