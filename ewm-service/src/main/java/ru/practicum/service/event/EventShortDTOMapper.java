package ru.practicum.service.event;

import org.mapstruct.Mapper;
import ru.practicum.api.event.EventShortDto;
import ru.practicum.jpa.entity.Event;
import ru.practicum.service.mapper.DefaultMapper;

@Mapper(componentModel = "spring")
public interface EventShortDTOMapper extends DefaultMapper<Event, EventShortDto> {
}
