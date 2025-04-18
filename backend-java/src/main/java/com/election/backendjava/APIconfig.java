package com.election.backendjava;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class APIconfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173",
                        "http://localhost:5175",
                        "http://localhost:5176",
                        "http://localhost:5177",
                        "http://localhost:5178",
                        "http://localhost:5179",
                        "http://localhost:8080",
                        "https://wiipuujaamee42-frontend.onrender.com",
                        "https://wiipuujaamee42-backend.onrender.com",
                        "http://demo-crazy.com",
                        "https://demo-crazy.com",
                        "http://*.hva.nl"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders(
                        "Accept",
                        "Content-Type",
                        "Origin",
                        "Authorization",
                        "X-Requested-With",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers"
                )
                .allowCredentials(true);
    }
}