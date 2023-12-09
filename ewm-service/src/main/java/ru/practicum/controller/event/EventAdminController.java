package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.event.EventFullDto;
import ru.practicum.api.event.UpdateEventAdminRequest;
import ru.practicum.exception.NotFoundException;
import ru.practicum.service.event.ApiEventService;
import ru.practicum.service.event.states.State;
import ru.practicum.service.logging.Logging;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventAdminController {
    private final ApiEventService apiEventService;
    private final DateTimeFormatter formatter;

    @Logging
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEvents(@RequestParam(value = "users", required = false) List<Long> usersIds,
                                        @RequestParam(value = "states", required = false) List<String> stringStates,
                                        @RequestParam(value = "categories", required = false) List<Long> catIds,
                                        @RequestParam(value = "rangeStart", required = false) String startString,
                                        @RequestParam(value = "rangeEnd", required = false) String endString,
                                        @RequestParam(value = "from", defaultValue = "0") Integer from,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List<State> states = Optional.ofNullable(stringStates)
                .orElseGet(ArrayList::new)
                .stream()
                .map(string -> State.from(string)
                        .orElseThrow(() -> new NotFoundException("Неизвестный state :" + string)))
                .collect(Collectors.toList());
        LocalDateTime start = Optional.ofNullable(startString)
                .map(string -> LocalDateTime.parse(string, formatter))
                .orElse(LocalDateTime.now());
        LocalDateTime end = Optional.ofNullable(endString)
                .map(string -> LocalDateTime.parse(string, formatter))
                .orElse(start.plusYears(100));

        return apiEventService.getAllEventsForAdmins(usersIds, states, catIds, start, end, from, size);
    }

    @Logging
    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto confirmEvent(@PathVariable("eventId") Long eventId,
                                     @RequestBody @Valid UpdateEventAdminRequest request) {
        return apiEventService.confirmEventByAdmin(eventId, request);
    }
}
