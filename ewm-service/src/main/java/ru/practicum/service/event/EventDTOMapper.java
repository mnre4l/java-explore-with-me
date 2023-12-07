package ru.practicum.service.event;

import org.mapstruct.Mapper;
import ru.practicum.api.event.EventFullDto;
import ru.practicum.jpa.entity.Event;
import ru.practicum.service.mapper.DefaultMapper;

@Mapper(componentModel = "spring")
public interface EventDTOMapper extends DefaultMapper<Event, EventFullDto> {
}
