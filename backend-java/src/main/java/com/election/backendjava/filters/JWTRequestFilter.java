package com.election.backendjava.filters;

import com.election.backendjava.APIconfig;
import com.election.backendjava.dto.JWToken;
import com.election.backendjava.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private static final Set <String> ALLOWED_PATHS = Set.of("/authentication/**");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        if (isWhiteListed(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);
        JWToken JWToken = null;

        try {

            JWToken = jwtService.decode(token);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        request.setAttribute(JwtService.JWT_ATTRIBUTE_NAME, JWToken);
        filterChain.doFilter(request, response);
    }
    private boolean isWhiteListed(String path) {
        return ALLOWED_PATHS.stream().anyMatch(p -> pathMatcher.match(p, path));
    }
}