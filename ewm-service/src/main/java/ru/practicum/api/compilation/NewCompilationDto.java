package ru.practicum.api.compilation;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class NewCompilationDto {
    @Size(min = 1, max = 50, message = "Title должен быть больше 1 и меньше 50")
    @NotBlank
    private String title;
    private Boolean pinned;
    @NotNull
    private List<Long> events;
}
