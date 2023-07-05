package com.quizapp.infrastructure.security;

import com.quizapp.core.interfaces.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtService authService;
    private UserRepository userRepository;

    public JwtAuthFilter(JwtService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");


        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = header.substring(7);
        var userId = authService.getUsername(token);

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request,response);
            return;
        }
        var userEntity = userRepository.findByEmail(userId).orElseThrow();

        if (!authService.validateToken(token,userEntity))
            return;
        var authToken = new UsernamePasswordAuthenticationToken(userEntity,null,userEntity.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request,response);
    }


}
