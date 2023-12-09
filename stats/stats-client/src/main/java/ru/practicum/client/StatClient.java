package ru.practicum.client;

import org.springframework.http.ResponseEntity;
import ru.practicum.api.EndpointHit;
import ru.practicum.api.ViewStats;

import java.util.List;

public interface StatClient {
    void saveRequest(EndpointHit endpointHit);

    ResponseEntity<List<ViewStats>> getStats(String startTimeString, String endTimeString, List<String> uris, Boolean unique);

    void changeBaseUrlOn(String newBaseUrl);
}
