package ru.practicum.client;

import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatClientImpl implements StatClient {
    private WebClient webClient;

    public StatClientImpl(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public void saveRequest(EndpointHit endpointHit) {
        webClient.post()
                .uri("/hit")
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public List<ViewStats> getStats(String startTimeString, String endTimeString, List<String> uris, Boolean unique) {
        return webClient.get()
                .uri("/stat", Map.of("start", startTimeString,
                        "end", endTimeString,
                        "uris", uris,
                        "unique", unique))
                .retrieve()
                .bodyToMono(ArrayList.class)
                .block();
    }

    @Override
    public void changeBaseUrlOn(String newBaseUrl) {
        this.webClient = WebClient.create(newBaseUrl);
    }
}
