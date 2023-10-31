package ru.practicum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.client.StatClient;
import ru.practicum.client.StatClientImpl;

@Configuration
public class Config {
    @Bean
    public StatClient statClient() {
        return BaseClientConfig
                .getApplicationContext()
                .getBean(StatClientImpl.class);
    }
}
