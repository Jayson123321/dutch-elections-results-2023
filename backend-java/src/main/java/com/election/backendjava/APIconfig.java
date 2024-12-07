package com.election.backendjava;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@Configuration
public class APIconfig implements WebMvcConfigurer {

    @Value("${api.issuer}")
    private String issuer;

    @Value("${api.passphrase}")
    private String passphrase;

    @Value("${api.tokenDurationOfValidity}")
    private int tokenDurationOfValidity;

    public String getIssuer() {
        return issuer;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public int getTokenDurationOfValidity() {
        return tokenDurationOfValidity;
    }

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
                        "http://demo-crazy.com",
                        "https://demo-crazy.com",
                        "http://*.hva.nl"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}