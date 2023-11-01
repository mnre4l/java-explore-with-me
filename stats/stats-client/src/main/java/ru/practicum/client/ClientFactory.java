package ru.practicum.client;

import org.springframework.beans.factory.annotation.Value;

public class ClientFactory {
    @Value("${stat.baseurl}")
    private static String baseUrl;

    static StatClient getDefaultClient() {
        return new StatClientImpl(baseUrl);
    }
}
