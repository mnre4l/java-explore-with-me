package ru.practicum.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.practicum.api.compilation.CompilationDto;
import ru.practicum.api.compilation.NewCompilationDto;
import ru.practicum.api.compilation.UpdateCompilationRequest;
import ru.practicum.api.page.Page;
import ru.practicum.exception.NotFoundException;
import ru.practicum.jpa.entity.Compilation;
import ru.practicum.jpa.entity.Event;
import ru.practicum.jpa.repository.CompilationRepository;
import ru.practicum.jpa.repository.EventRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;


    @Override
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Compilation compilation = Compilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned() != null && newCompilationDto.getPinned())
                .build();

        handleSettingEvents(compilation, newCompilationDto.getEvents());
        compilationRepository.save(compilation);
        return compilationMapper.toDTO(compilation);
    }


    @Override
    public Collection<CompilationDto> getAll(Integer from, Integer size) {
        List<Compilation> compilations = compilationRepository.findAll(new Page(from, size, Sort.unsorted()))
                .getContent();

        return compilationMapper.toDTO(compilations);
    }

    @Override
    public CompilationDto get(Long id) {
        return compilationRepository.findById(id)
                .map(compilationMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Не найдена подборка id = " + id));
    }

    @Override
    public Collection<CompilationDto> getCompilationsByPinned(Boolean pinned, final Integer from, final Integer size) {
        return Optional.ofNullable(pinned)
                .map(p -> compilationRepository.findCompilationByPinned(p, new Page(from, size, Sort.unsorted())))
                .map(compilationMapper::toDTO)
                .orElse(Collections.emptyList());
    }

    @Override
    public CompilationDto update(Long compilationId, UpdateCompilationRequest update) {
        Compilation compilation = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException("Не найдена подборка id = " + compilationId));

        handleTitleUpdate(compilation, update.getTitle());
        handlePinnedUpdate(compilation, update.getPinned());
        handleEventsUpdate(compilation, update.getEvents());
        compilationRepository.save(compilation);
        return compilationMapper.toDTO(compilation);
    }

    @Override
    public void delete(Long id) {
        Compilation compilation = compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Не найдена подборка id = " + id));
        compilationRepository.delete(compilation);
    }

    private void handleTitleUpdate(Compilation compilation, @Nullable String title) {
        Optional.ofNullable(title)
                .ifPresent(compilation::setTitle);
    }

    private void handlePinnedUpdate(Compilation compilation, @Nullable Boolean pinned) {
        Optional.ofNullable(pinned)
                .ifPresent(compilation::setPinned);
    }

    private void handleEventsUpdate(Compilation compilation, @Nullable List<Long> eventsIds) {
        Optional.ofNullable(eventsIds)
                .filter(ids -> !ids.equals(
                        compilation.getEvents()
                                .stream()
                                .map(Event::getId)
                                .collect(Collectors.toList())
                ))
                .map(eventRepository::findAllById)
                .ifPresent(compilation::setEvents);
    }

    private void handleSettingEvents(Compilation compilation, @Nullable Collection<Long> eventsIds) {
        Optional.ofNullable(eventsIds)
                .map(eventRepository::findAllById)
                .ifPresent(compilation::setEvents);
    }
}
