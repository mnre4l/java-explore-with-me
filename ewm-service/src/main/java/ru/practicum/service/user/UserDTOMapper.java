package ru.practicum.service.user;

import org.mapstruct.Mapper;
import ru.practicum.api.user.UserDto;
import ru.practicum.jpa.entity.User;
import ru.practicum.service.mapper.DefaultMapper;

@Mapper(componentModel = "spring")
public interface UserDTOMapper extends DefaultMapper<User, UserDto> {
}
