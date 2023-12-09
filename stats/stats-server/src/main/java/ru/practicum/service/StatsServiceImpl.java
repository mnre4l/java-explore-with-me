package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.api.EndpointHit;
import ru.practicum.api.ViewStats;
import ru.practicum.model.EndPointRequestRecord;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;
    private final Mapper mapper;

    @Override
    public void saveRequest(EndpointHit endpointHit) {
        log.info("Сохраняем: {}", endpointHit);
        EndPointRequestRecord record = mapper.fromDto(endpointHit);
        record = repository.save(record);
        log.info("Сохранено: {}", record);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info("Запрос статистики для start = {}, end = {}, uris = {}, unique = {}", start, end, uris, unique);
        if (uris == null || uris.isEmpty()) {
            uris = repository.findAll().stream()
                    .map(record -> record.getUri())
                    .collect(Collectors.toList());
        }
        return unique ? repository.getUniqueStats(start, end, uris) : repository.getStats(start, end, uris);
    }
}
