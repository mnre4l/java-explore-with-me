package ru.practicum.client;

public class ClientFactory {
    public static StatClient getDefaultClient(String url) {
        return new StatClientImpl(url);
    }
}
