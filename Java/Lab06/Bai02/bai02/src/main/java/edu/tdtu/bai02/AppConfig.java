package edu.tdtu.bai02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Product product1() {
        return new Product(1, "Laptop", 1500, "A high-performance laptop");
    }
}
