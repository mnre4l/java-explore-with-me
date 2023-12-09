package ru.practicum.service.crud.defaults;

public interface DefaultCreateService<CMD, D> {
    D create(CMD createDTO);
}
