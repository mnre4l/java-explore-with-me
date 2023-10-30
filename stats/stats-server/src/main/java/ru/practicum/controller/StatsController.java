package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping
public class StatsController {
    private final StatsService service;
    private final DateTimeFormatter dateTimeFormatter;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated
    public void saveRequest(@RequestBody @Valid final EndpointHit endpointHit) {
        log.info("/hit получен для {}", endpointHit);
        service.saveRequest(endpointHit);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStats> getStats(@RequestParam(value = "start") @NotBlank String startTimeString,
                                    @RequestParam(value = "end") @NotBlank String endTimeString,
                                    @RequestParam(value = "uris") List<String> uris,
                                    @RequestParam(value = "unique", required = false, defaultValue = "false") Boolean unique) {
        log.info("/stats получен для start = {}, end = {}, uris = {}, unique = {}",
                startTimeString,
                endTimeString,
                uris,
                unique);

        LocalDateTime start = LocalDateTime.parse(startTimeString, dateTimeFormatter);
        LocalDateTime end = LocalDateTime.parse(endTimeString, dateTimeFormatter);

        return service.getStats(start, end, uris, unique);
    }

}
