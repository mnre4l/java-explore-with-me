package ru.practicum.service.crud.byuser;

public interface UserRequestUpdateService<D, ID, EID, R> {
    D updateByUser(ID userId, EID id, R request);
}
