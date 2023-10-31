package ru.practicum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BaseClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:9090");
    }
}
