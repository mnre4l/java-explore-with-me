package ru.practicum.service.event;

import ru.practicum.service.event.states.State;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminActionsEventService<D, ID, UID, CID, R> {
    D confirmEventByAdmin(ID eventId, R request);

    List<D> getAllEventsForAdmins(List<UID> usersIds,
                                  List<State> states,
                                  List<CID> catIds,
                                  LocalDateTime start,
                                  LocalDateTime end,
                                  Integer from,
                                  Integer size);
}
