package com.election.backendjava;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class APIconfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("https://wiipuujaamee42-frontend.onrender.com");
        config.addAllowedOrigin("http://wiipuujaamee42-frontend.onrender.com");
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://localhost:5175");
        config.addAllowedOrigin("http://localhost:5176");
        config.addAllowedOrigin("http://localhost:5177");
        config.addAllowedOrigin("http://localhost:5178");
        config.addAllowedOrigin("http://localhost:5179");
        config.addAllowedOrigin("https://wiipuujaamee42-frontend-v1-0.onrender.com");
        config.addAllowedOrigin("https://ewa-back-end-4lw7.onrender.com");
        config.addAllowedOrigin("https://wiipuujaamee42-backend.onrender.com");
        config.addAllowedOrigin("http://*.hva.nl");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}