package com.quizapp.infrastructure.security;

import com.quizapp.core.interfaces.services.AuthMethodService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService implements AuthMethodService {
    @Value("${app.jwt.secret}")
    private String secretKey;

    public String getUsername(String jwt){
        return getClaim(jwt,Claims::getSubject);
    }


    public String generateToken(UserDetails userDetails){
        return buildToken(userDetails);
    }


    public boolean validateToken(String jwt, UserDetails userDetails){
        var user = getUsername(jwt);
        return user.equals(userDetails.getUsername()) && getClaim(jwt,Claims::getExpiration).after(Date.from(Instant.now()));
    }

    private String buildToken(
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1024*60*60))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimFunc){
        Claims claims = getClaims(token);
        return claimFunc.apply(claims);
    }

    private Claims getClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        var key = secretKey.getBytes();
        return Keys.hmacShaKeyFor(key);
    }
}
