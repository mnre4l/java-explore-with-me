package ru.practicum.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.api.EndpointHit;
import ru.practicum.api.ViewStats;

import java.util.List;

public class StatClientImpl implements StatClient {
    private WebClient webClient;

    public StatClientImpl(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public void saveRequest(EndpointHit endpointHit) {
        webClient.post()
                .uri("/hit")
                .bodyValue(endpointHit)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public ResponseEntity<List<ViewStats>> getStats(String startTimeString, String endTimeString, List<String> uris, Boolean unique) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("uris", uris)
                        .queryParam("start", startTimeString)
                        .queryParam("end", endTimeString)
                        .queryParam("unique", unique)
                        .build()
                )
                .retrieve()
                .toEntityList(ViewStats.class)
                .block();
    }

    @Override
    public void changeBaseUrlOn(String newBaseUrl) {
        this.webClient = WebClient.create(newBaseUrl);
    }
}
