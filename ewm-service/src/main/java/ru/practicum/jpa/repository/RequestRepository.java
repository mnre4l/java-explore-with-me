package ru.practicum.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.jpa.entity.Event;
import ru.practicum.jpa.entity.EventParticipationRequest;
import ru.practicum.jpa.entity.User;

import java.util.List;

public interface RequestRepository extends JpaRepository<EventParticipationRequest, Long> {
    List<EventParticipationRequest> findAllByEvent(Event event);

    List<EventParticipationRequest> findAllByRequester(User requester);
}
