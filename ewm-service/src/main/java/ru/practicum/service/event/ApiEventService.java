package ru.practicum.service.event;

import ru.practicum.api.event.*;
import ru.practicum.service.crud.byuser.UserRequestCreateService;
import ru.practicum.service.crud.byuser.UserRequestRetrieveAllService;
import ru.practicum.service.crud.byuser.UserRequestUpdateService;
import ru.practicum.service.crud.byuser.UserRetrieveService;
import ru.practicum.service.crud.defaults.DefaultGetterService;
import ru.practicum.service.event.sort.EventSort;

import java.time.LocalDateTime;
import java.util.List;

public interface ApiEventService extends UserRequestCreateService<EventFullDto, Long, NewEventDto>,
        UserRequestRetrieveAllService<EventShortDto, Long>, DefaultGetterService<Long, EventFullDto>,
        AdminActionsEventService<EventFullDto, Long, Long, Long, UpdateEventAdminRequest>,
        UserRequestUpdateService<EventFullDto, Long, Long, UpdateEventUserRequest>,
        UserRetrieveService<EventFullDto, Long, Long> {

    List<EventShortDto> getPublicEvents(String text,
                                        List<Long> categories,
                                        Boolean paid,
                                        LocalDateTime start,
                                        LocalDateTime end,
                                        Boolean isAvailable,
                                        EventSort eventSort,
                                        Integer from,
                                        Integer size);

}
