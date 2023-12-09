package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.event.EventFullDto;
import ru.practicum.api.event.EventShortDto;
import ru.practicum.api.event.NewEventDto;
import ru.practicum.api.event.UpdateEventUserRequest;
import ru.practicum.service.event.ApiEventService;
import ru.practicum.service.logging.Logging;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class EventPrivateController {
    private final ApiEventService apiEventService;

    @Logging
    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEventByUser(@PathVariable("userId") Long userId,
                                          @RequestBody @Valid NewEventDto newEventDto) {
        return apiEventService.create(userId, newEventDto);
    }

    @Logging
    @GetMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventsByInitiator(@PathVariable("userId") Long initiatorId,
                                                    @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return List.copyOf(apiEventService.getAllByUser(initiatorId, from, size));
    }

    @Logging
    @GetMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventById(@PathVariable("userId") Long userId,
                                     @PathVariable("eventId") Long eventId) {
        return apiEventService.get(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(@PathVariable("userId") Long userId,
                                    @PathVariable("eventId") Long eventId,
                                    @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        return apiEventService.updateByUser(userId, eventId, updateEventUserRequest);
    }
}
