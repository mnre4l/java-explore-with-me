package ru.practicum.api.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewCategoryDto {
    @Size(min = 1, max = 50, message = "Name должно быть больше 1 и меньше 50")
    @NotBlank
    private String name;
}
