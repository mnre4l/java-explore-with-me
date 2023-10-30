package ru.practicum.client;

import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;

import java.util.List;

public interface StatClient {
    void saveRequest(EndpointHit endpointHit);

    List<ViewStats> getStats(String startTimeString, String endTimeString, List<String> uris, Boolean unique);
}
