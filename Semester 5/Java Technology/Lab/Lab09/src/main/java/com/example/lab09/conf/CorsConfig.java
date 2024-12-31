package com.example.lab09.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig {
    @Bean
    public WebMvcConfigurer getCorsConfiguration(){
        return new WebMvcConfigurer () {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry corsRegistry) {
                corsRegistry.addMapping("/api/**")
                        .allowedOrigins("https://localhost:9090")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                        .allowedHeaders("*")
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }
}
