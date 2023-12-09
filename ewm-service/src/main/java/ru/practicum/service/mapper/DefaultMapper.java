package ru.practicum.service.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

public interface DefaultMapper<E, D> {
    D toDTO(E e);

    default Collection<D> toDTO(Collection<E> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
