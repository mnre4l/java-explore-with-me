package ru.practicum.service.crud.defaults;

import java.util.Collection;

public interface DefaultGetterService<ID, D> {
    Collection<D> getAll(Integer from, Integer size);

    D get(ID id);
}
