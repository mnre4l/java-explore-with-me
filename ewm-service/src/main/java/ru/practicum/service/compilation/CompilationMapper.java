package ru.practicum.service.compilation;

import org.mapstruct.Mapper;
import ru.practicum.api.compilation.CompilationDto;
import ru.practicum.jpa.entity.Compilation;
import ru.practicum.service.mapper.DefaultMapper;

@Mapper(componentModel = "spring")
public interface CompilationMapper extends DefaultMapper<Compilation, CompilationDto> {
}
