package com.quizapp.web.controllers;

import com.quizapp.core.interfaces.repository.UserRepository;
import com.quizapp.core.interfaces.services.AuthenticationService;
import com.quizapp.web.dto.auth.UserDto;
import com.quizapp.web.dto.auth.SignInRequestDto;
import com.quizapp.web.dto.auth.SignInResponseDto;
import com.quizapp.web.dto.auth.SignUpRequestDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationService authenticationService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @PostMapping("signup")
    public ResponseEntity signUp(@Valid @RequestBody SignUpRequestDto dto){
        try {
            var user = authenticationService.signUp(
                    dto.getFirstname(),
                    dto.getLastname(),
                    dto.getEmail(),
                    dto.getPassword()
            );
            var token = authenticationService.signIn(
                    dto.getEmail(),
                    dto.getPassword()
            );
            var userDto = new UserDto(user.getId(), user.getEmail());
            var responseDto = new SignInResponseDto(userDto, token);

            return ResponseEntity.ok(new SignInResponseDto(userDto, token));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("signin")
    public ResponseEntity<SignInResponseDto> signIn(@Valid @RequestBody SignInRequestDto dto){
        var token = authenticationService.signIn(
                dto.getEmail(),
                dto.getPassword()
        );

        var user = userRepository.findByEmail(dto.getEmail()).orElseThrow();
        var userDto = new UserDto(user.getId(), user.getEmail());
        var responseDto = new SignInResponseDto(userDto,token);

        return ResponseEntity.ok(responseDto);
    }
}
