package ru.practicum.service.crud.byuser;

public interface UserRequestCreateService<D, ID, C> {
    D create(ID userRequestFromId, C c);
}
