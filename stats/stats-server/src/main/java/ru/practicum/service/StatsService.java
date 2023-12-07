package ru.practicum.service;

import ru.practicum.api.EndpointHit;
import ru.practicum.api.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void saveRequest(EndpointHit endpointHit);

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
