package com.election.backendjava;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class APIconfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "https://wiipuujaamee42-frontend.onrender.com",
                        "http://wiipuujaamee42-frontend.onrender.com",
                        "http://localhost:5173",
                        "http://localhost:5175",
                        "http://localhost:5176",
                        "http://localhost:5177",
                        "http://localhost:5178",
                        "http://localhost:5179",
                        "https://wiipuujaamee42-frontend-v1-0.onrender.com",
                        "https://ewa-back-end-4lw7.onrender.com",
                        "https://wiipuujaamee42-backend.onrender.com",
                        "http://*.hva.nl"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Origin")
                .exposedHeaders("Content-Type")
                .allowCredentials(true);
    }
}