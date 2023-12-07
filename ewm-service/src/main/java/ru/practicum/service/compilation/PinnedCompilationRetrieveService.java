package ru.practicum.service.compilation;

import java.util.Collection;

public interface PinnedCompilationRetrieveService<D> {
    Collection<D> getCompilationsByPinned(Boolean pinned, Integer from, Integer size);
}
