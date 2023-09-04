package com.redmath.assignment.bankingapplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/") // Specify the URL pattern you want to allow
                .allowedOrigins("http://localhost:3000") // Specify the origin(s) you want to allow
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify the HTTP methods you want to allow
                .allowCredentials(true) // Allow cookies and authentication headers
                .exposedHeaders("Authorization"); // Expose the Authorization header for the frontend

        // Allow OPTIONS preflight requests
        registry
                .addMapping("*")
                .allowedOrigins("*")
                .allowedMethods("GET")
                .allowedMethods("POST")
                .allowedMethods("OPTIONS");
    }
}