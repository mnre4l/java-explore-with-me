package ru.practicum.api.compilation;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateCompilationRequest {
    private List<Long> events;
    private Boolean pinned;
    @Size(min = 1, max = 50, message = "Title должен быть больше 1 и меньше 50")
    private String title;
}
