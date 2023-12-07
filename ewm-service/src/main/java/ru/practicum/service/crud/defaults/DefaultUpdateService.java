package ru.practicum.service.crud.defaults;

public interface DefaultUpdateService<U, D> {

    D update(Long id, U update);

    void delete(Long id);
}
