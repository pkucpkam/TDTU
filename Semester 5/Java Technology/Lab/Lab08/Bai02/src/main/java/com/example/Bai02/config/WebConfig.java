package com.example.Bai02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class WebConfig {

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        System.out.println("HiddenHttpMethodFilter is being used");
        return new HiddenHttpMethodFilter();
    }
}
