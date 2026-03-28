package com.ou.springdemo.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ou.springdemo.config.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UserDetails userDetails) {
        // Implementation for generating access token
        return buildToken(
            userDetails.getUsername(),
            jwtProperties.getAccessTokenExpirationMs()
        );
    }

    public String generateRefreshToken(UserDetails userDetails) {
        // Implementation for generating refresh token
        return buildToken(
            userDetails.getUsername(),
            jwtProperties.getRefreshTokenExpirationMs()
        );
    }

    private String buildToken(String subject, long expirationMs) {
        // Placeholder for token building logic using JJWT
        return Jwts.builder()
            .subject(subject)
            .issuedAt(new Date (System.currentTimeMillis()))
            .expiration(new Date (System.currentTimeMillis() + expirationMs))
            .signWith(getSigningKey())
            .compact();
    }

    //Chứng thực
    public String extractUsername(String token) {
        // Implementation for extracting username from token
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        // Implementation for extracting expiration date from token
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        // Placeholder for extracting all claims from token
        return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

}
