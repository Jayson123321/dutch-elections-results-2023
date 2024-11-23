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
                        "https://demo-crazy.com",
                        "http://demo-crazy.com",
                        "http://localhost:5173",
                        "http://localhost:5175",
                        "http://localhost:5176",
                        "http://localhost:5177",
                        "http://localhost:5178",
                        "http://localhost:5179",
                        "http://*.hva.nl"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "X-Requested-With", "Accept", "Origin")
                .exposedHeaders("Content-Type")
                .allowCredentials(true);
    }
}