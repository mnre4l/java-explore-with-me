package ru.practicum.controller.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.api.compilation.CompilationDto;
import ru.practicum.service.compilation.CompilationService;
import ru.practicum.service.logging.Logging;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;

    @Logging
    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(value = "pinned", defaultValue = "false") Boolean pinned,
                                                @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return List.copyOf(compilationService.getCompilationsByPinned(pinned, from, size));
    }

    @Logging
    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable("compId") Long compId) {
        return compilationService.get(compId);
    }
}
