package ru.practicum.service.crud.byuser;

import java.util.Collection;

public interface UserRequestRetrieveAllService<D, ID> {
    Collection<D> getAllByUser(ID id, Integer from, Integer size);
}
