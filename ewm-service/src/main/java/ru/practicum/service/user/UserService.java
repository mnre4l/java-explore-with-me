package ru.practicum.service.user;

import ru.practicum.api.user.NewUserRequest;
import ru.practicum.api.user.UserDto;
import ru.practicum.service.crud.defaults.DefaultCreateService;

import java.util.List;

public interface UserService extends DefaultCreateService<NewUserRequest, UserDto> {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    void deleteUser(Long id);
}
