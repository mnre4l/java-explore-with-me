package ru.practicum.controller.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.compilation.CompilationDto;
import ru.practicum.api.compilation.NewCompilationDto;
import ru.practicum.api.compilation.UpdateCompilationRequest;
import ru.practicum.service.compilation.CompilationService;
import ru.practicum.service.logging.Logging;

import javax.validation.Valid;

@Validated
@RequestMapping(path = "/admin/compilations")
@RestController
@RequiredArgsConstructor
public class CompilationAdminController {
    private final CompilationService compilationService;

    @Logging
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return compilationService.create(newCompilationDto);
    }

    @Logging
    @PatchMapping("/{compilationId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@RequestBody @Valid UpdateCompilationRequest updateCompilationRequest,
                                            @PathVariable("compilationId") Long id) {
        return compilationService.update(id, updateCompilationRequest);
    }

    @Logging
    @DeleteMapping("/{compilationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable("compilationId") Long id) {
        compilationService.delete(id);
    }
}
