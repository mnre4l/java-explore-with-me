package ru.practicum.service.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.api.request.ParticipationRequestDto;
import ru.practicum.jpa.entity.EventParticipationRequest;
import ru.practicum.service.mapper.DefaultMapper;

@Mapper(componentModel = "spring")
public interface RequestDTOMapper extends DefaultMapper<EventParticipationRequest, ParticipationRequestDto> {
    @Override
    @Mapping(target = "event", source = "entity.event.id")
    @Mapping(target = "requester", source = "entity.requester.id")
    ParticipationRequestDto toDTO(EventParticipationRequest entity);
}
