package com.election.backendjava.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String passphrase;

    public JwtAuthenticationFilter(String passphrase) {
        this.passphrase = passphrase;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Key key = new SecretKeySpec(passphrase.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

                // Set authentication context
                // SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Handle token parsing exception
            }
        }
        chain.doFilter(request, response);
    }
}