package com.election.backendjava;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class APIconfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://demo-crazy.com","http://demo-crazy.com/login","http://*.hva.nl", "http://localhost:5173/Register", "http://localhost:5173/Login")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
