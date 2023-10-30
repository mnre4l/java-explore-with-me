package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.model.EndPointRequestRecord;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;
    private final Mapper mapper;

    @Override
    public void saveRequest(EndpointHit endpointHit) {
        EndPointRequestRecord record = mapper.fromDto(endpointHit);
        record = repository.save(record);
        log.info("Сохранено: {}", record);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return unique ? repository.getUniqueStats(start, end, uris) : repository.getStats(start, end, uris);
    }
}
