package ru.practicum.service.compilation;

import ru.practicum.api.compilation.CompilationDto;
import ru.practicum.api.compilation.NewCompilationDto;
import ru.practicum.api.compilation.UpdateCompilationRequest;
import ru.practicum.service.crud.defaults.DefaultCreateService;
import ru.practicum.service.crud.defaults.DefaultGetterService;
import ru.practicum.service.crud.defaults.DefaultUpdateService;

public interface CompilationService extends DefaultCreateService<NewCompilationDto, CompilationDto>,
        PinnedCompilationRetrieveService<CompilationDto>,
        DefaultGetterService<Long, CompilationDto>, DefaultUpdateService<UpdateCompilationRequest, CompilationDto> {
}
