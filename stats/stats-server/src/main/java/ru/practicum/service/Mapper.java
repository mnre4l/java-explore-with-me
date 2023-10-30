package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.dto.EndpointHit;
import ru.practicum.model.EndPointRequestRecord;

@Component
@RequiredArgsConstructor
public class Mapper {
    private final ModelMapper modelMapper;

    public EndPointRequestRecord fromDto(EndpointHit endpointHit) {
        return modelMapper.map(endpointHit, EndPointRequestRecord.class);
    }
}
