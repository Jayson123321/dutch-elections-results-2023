package com.election.backendjava.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "6ef37456aa4355eeb067a241c3178104f4ea7c3cb97f5ca479d59909e4edfea4aae76e638ea32649fc22fc3b74c8b8e11ed7a8513a9690d97415cee946b7b3859cd6c3f7abba0e848339966a0597ae33913276e1af1d36d2990054a25d3bb92a";
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    private Key key() {
        byte[] keyByte = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(keyByte, SignatureAlgorithm.HS256.getJcaName());
    }

    public JwtUtil() {
                if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            throw new IllegalArgumentException("JWT_SECRET_KEY is not specified!");
        }
    }

    // Generate JWT token
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract user ID from the token
    public String extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // The ID is stored as 'subject' here
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

    }

    // Extract email from token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}