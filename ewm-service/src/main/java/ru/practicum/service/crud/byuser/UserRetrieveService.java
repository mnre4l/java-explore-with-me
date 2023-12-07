package ru.practicum.service.crud.byuser;

public interface UserRetrieveService<D, ID, EID> {
    D get(ID userId, EID id);
}
