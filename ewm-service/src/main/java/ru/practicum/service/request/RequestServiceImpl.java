package ru.practicum.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.api.request.EventRequestStatusUpdateRequest;
import ru.practicum.api.request.EventRequestStatusUpdateResult;
import ru.practicum.api.request.ParticipationRequestDto;
import ru.practicum.exception.ForbiddenToChangeException;
import ru.practicum.exception.NoNeedToConfirmRequestsException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.RequestProcessingException;
import ru.practicum.jpa.entity.Event;
import ru.practicum.jpa.entity.EventParticipationRequest;
import ru.practicum.jpa.entity.User;
import ru.practicum.jpa.repository.EventRepository;
import ru.practicum.jpa.repository.RequestRepository;
import ru.practicum.jpa.repository.UserRepository;
import ru.practicum.service.event.states.State;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final RequestDTOMapper requestDTOMapper;

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        Event event = Optional.ofNullable(eventId)
                .map(id -> eventRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Не найден event id = " + eventId)))
                .filter(ev -> !ev.getInitiator().getId().equals(userId))
                .filter(ev -> ev.getState().equals(State.PUBLISHED))
                .filter(ev -> ev.getParticipantLimit() > ev.getConfirmedRequests() || ev.getParticipantLimit() == 0)
                .filter(ev -> !requestRepository.findAllByEvent(ev).stream()
                        .map(request -> request.getRequester().getId())
                        .collect(Collectors.toList())
                        .contains(userId))
                .orElseThrow(() -> new RequestProcessingException("Неверные параметры в запросе на участие"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь id = " + userId));
        EventParticipationRequest request = getNewRequestModel(user, event);

        requestRepository.save(request);
        return requestDTOMapper.toDTO(request);
    }

    @Override
    public List<ParticipationRequestDto> getRequestsToUserEvent(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден user id = " + userId));
        Event event = eventRepository.findById(eventId)
                .filter(ev -> ev.getInitiator().getId().equals(user.getId()))
                .orElseThrow(() -> new NotFoundException("Не найден event id = " + eventId));

        return List.copyOf(requestDTOMapper.toDTO(requestRepository.findAllByEvent(event)));
    }

    @Override
    public EventRequestStatusUpdateResult confirmRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден user id = " + userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найден event id = " + eventId));

        checkUserCanConfirmRequests(user, event);
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            throw new NoNeedToConfirmRequestsException("Отключена модерация заявок или отсутсвует лимит на участников");
        }

        List<EventParticipationRequest> requests = requestRepository.findAllById(updateRequest.getRequestIds());

        return handleEventRequests(event, requests, updateRequest.getStatus());
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        EventParticipationRequest request = Optional.ofNullable(requestId)
                .map(id -> requestRepository.findById(requestId)
                        .orElseThrow(() -> new NotFoundException("Не найден запрос id = " + id)))
                .filter(req -> req.getRequester().getId().equals(userId))
                .orElseThrow(() -> new ForbiddenToChangeException("Запрос не принадлежит пользователю"));

        request.setStatus(Status.CANCELED);
        requestRepository.save(request);
        return requestDTOMapper.toDTO(request);
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        var requests = Optional.ofNullable(userId)
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Не найден пользователь id = " + userId)))
                .map(requestRepository::findAllByRequester)
                .orElse(Collections.emptyList());
        return List.copyOf(requestDTOMapper.toDTO(requests));
    }

    private EventRequestStatusUpdateResult handleEventRequests(Event event, List<EventParticipationRequest> requests, Status status) {
        Long participantLimit = event.getParticipantLimit();
        Long confirmedRequests = event.getConfirmedRequests();

        if (participantLimit.equals(confirmedRequests)) throw new ForbiddenToChangeException("Лимит заявок");

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        result.setConfirmedRequests(new ArrayList<>());
        result.setRejectedRequests(new ArrayList<>());
        Iterator<EventParticipationRequest> iterator = requests.iterator();

        while (confirmedRequests < participantLimit && iterator.hasNext()) {
            EventParticipationRequest request = iterator.next();
            if (!request.getStatus().equals(Status.PENDING)) {
                throw new ForbiddenToChangeException("Заявка уже была рассмотрена: id = " + request.getId());
            }
            request.setStatus(status);
            if (status.equals(Status.CONFIRMED)) {
                confirmedRequests++;
            }
        }
        while (iterator.hasNext()) {
            EventParticipationRequest request = iterator.next();
            request.setStatus(Status.REJECTED);
        }
        for (EventParticipationRequest request : requests) {
            if (request.getStatus().equals(Status.CONFIRMED)) {
                result.getConfirmedRequests().add(requestDTOMapper.toDTO(request));
            } else {
                result.getRejectedRequests().add(requestDTOMapper.toDTO(request));
            }
        }
        event.setConfirmedRequests(confirmedRequests);
        eventRepository.save(event);
        requestRepository.saveAll(requests);
        return result;
    }


    private void checkUserCanConfirmRequests(User user, Event event) {
        Long userId = user.getId();

        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenToChangeException("Пользователь не является создателем ивента");
        }
    }

    private EventParticipationRequest getNewRequestModel(User requester, Event event) {
        EventParticipationRequest request = new EventParticipationRequest();

        request.setRequester(requester);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            request.setStatus(Status.CONFIRMED);

            Long confirmedRequests = event.getConfirmedRequests();
            event.setConfirmedRequests(++confirmedRequests);
            eventRepository.save(event);
        } else {
            request.setStatus(Status.PENDING);
        }
        return request;
    }
}
