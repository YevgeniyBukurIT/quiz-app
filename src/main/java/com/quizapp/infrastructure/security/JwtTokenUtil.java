package com.quizapp.infrastructure.security;

import com.quizapp.core.models.user.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.Date;

public class JwtTokenUtil {
    private static final long EXPIRE_DURATION = 2 * 60 * 60 * 1000; // 24 hour

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.audience}")
    private String audience;

    @Value("${app.jwt.issuer}")
    private String issuer;

    public String generateAccessToken(AppUser user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
                .setIssuer(issuer)
                .setAudience(audience)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
