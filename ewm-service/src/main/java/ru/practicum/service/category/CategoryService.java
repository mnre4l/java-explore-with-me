package ru.practicum.service.category;

import ru.practicum.api.category.CategoryDto;
import ru.practicum.api.category.NewCategoryDto;
import ru.practicum.service.crud.defaults.DefaultCreateService;
import ru.practicum.service.crud.defaults.DefaultGetterService;
import ru.practicum.service.crud.defaults.DefaultUpdateService;

public interface CategoryService extends DefaultUpdateService<NewCategoryDto, CategoryDto>,
        DefaultCreateService<NewCategoryDto, CategoryDto>, DefaultGetterService<Long, CategoryDto> {
}
