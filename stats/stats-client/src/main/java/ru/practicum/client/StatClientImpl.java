package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StatClientImpl implements StatClient, Cloneable {
    private final WebClient webClient;

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
}
