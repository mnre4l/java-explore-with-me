package ru.practicum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BaseClientConfig {
    private static ApplicationContext applicationContext;

    @Autowired
    public BaseClientConfig(ApplicationContext context) {
        applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:9090");
    }
}
