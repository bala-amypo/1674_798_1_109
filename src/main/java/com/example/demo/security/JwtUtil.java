// src/main/java/com/example/demo/security/JwtUtil.java
package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public class JwtUtil {

    private final SecretKey secretKey;
    private final long jwtExpirationInMs;

    // constructor used in tests: new JwtUtil(testSecret, 60L * 60L * 1000L);
    public JwtUtil(byte[] secret, long jwtExpirationInMs) {
        // create HMAC key from raw bytes
        this.secretKey = Keys.hmacShaKeyFor(secret);
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String generateToken(Long userId, String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                // CHANGED: use HS256 instead of HS512 so test secret is strong enough
                .signWith(secretKey, SignatureAlgorithm.HS256)   // changed-by-you
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);
    }

    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);  // will throw if invalid/expired
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
}
