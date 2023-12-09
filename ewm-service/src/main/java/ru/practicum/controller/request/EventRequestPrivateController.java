package ru.practicum.controller.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.request.EventRequestStatusUpdateRequest;
import ru.practicum.api.request.EventRequestStatusUpdateResult;
import ru.practicum.api.request.ParticipationRequestDto;
import ru.practicum.service.logging.Logging;
import ru.practicum.service.request.RequestService;

import java.util.List;

@Validated
@RequestMapping(path = "/users")
@RestController
@RequiredArgsConstructor
public class EventRequestPrivateController {
    private final RequestService requestService;

    @Logging
    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable("userId") Long userId,
                                                 @RequestParam("eventId") Long eventId) {
        return requestService.createRequest(userId, eventId);
    }

    @Logging
    @GetMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getUserRequest(@PathVariable("userId") Long userId) {
        return requestService.getUserRequests(userId);
    }

    @Logging
    @GetMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequestsByEvent(@PathVariable("userId") Long userId,
                                                            @PathVariable("eventId") Long eventId) {
        return requestService.getRequestsToUserEvent(userId, eventId);
    }

    @Logging
    @PatchMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult confirmRequestsByEvent(@PathVariable("userId") Long userId,
                                                                 @PathVariable("eventId") Long eventId,
                                                                 @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        return requestService.confirmRequests(userId, eventId, updateRequest);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelRequest(@PathVariable("userId") Long userId,
                                                 @PathVariable("requestId") Long requestId) {
        return requestService.cancelRequest(userId, requestId);
    }
}
