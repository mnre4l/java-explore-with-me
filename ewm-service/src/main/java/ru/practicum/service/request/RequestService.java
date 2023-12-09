package ru.practicum.service.request;

import ru.practicum.api.request.EventRequestStatusUpdateRequest;
import ru.practicum.api.request.EventRequestStatusUpdateResult;
import ru.practicum.api.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto createRequest(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequestsToUserEvent(Long userId, Long eventId);

    EventRequestStatusUpdateResult confirmRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getUserRequests(Long userId);
}
