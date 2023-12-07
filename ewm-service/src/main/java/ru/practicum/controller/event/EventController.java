package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.event.EventFullDto;
import ru.practicum.api.event.EventShortDto;
import ru.practicum.exception.BadDateRangeException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.service.event.ApiEventService;
import ru.practicum.service.event.sort.EventSort;
import ru.practicum.service.logging.Logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class EventController {
    private final ApiEventService apiEventService;
    private final DateTimeFormatter formatter;

    @Logging
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventById(@PathVariable("id") Long id) {
        return apiEventService.get(id);
    }

    @Logging
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getPublicEvents(@RequestParam(value = "text", defaultValue = "") String text,
                                               @RequestParam(value = "categories", required = false) List<Long> categories,
                                               @RequestParam(value = "paid", required = false) Boolean paid,
                                               @RequestParam(value = "rangeStart", required = false) String startString,
                                               @RequestParam(value = "rangeEnd", required = false) String endString,
                                               @RequestParam(value = "onlyAvailable", required = false) Boolean isAvailable,
                                               @RequestParam(value = "sort", defaultValue = "EVENT_DATE") String sort,
                                               @RequestParam(value = "from", defaultValue = "0") Integer from,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        EventSort eventSort = EventSort.from(sort)
                .orElseThrow(() -> new NotFoundException("Недоступна сортировка: " + sort));
        LocalDateTime start = Optional.ofNullable(startString)
                .map(string -> LocalDateTime.parse(string, formatter))
                .orElse(LocalDateTime.now());
        LocalDateTime end = Optional.ofNullable(endString)
                .map(string -> LocalDateTime.parse(string, formatter))
                .orElse(start.plusYears(100));

        if (!end.isAfter(start)) throw new BadDateRangeException("Некорректный временной промежуток");
        return apiEventService.getPublicEvents(
                text,
                categories,
                paid,
                start,
                end,
                isAvailable,
                eventSort,
                from,
                size
        );
    }
}
