package ru.practicum.service.category;

import org.mapstruct.Mapper;
import ru.practicum.api.category.CategoryDto;
import ru.practicum.jpa.entity.Category;
import ru.practicum.service.mapper.DefaultMapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends DefaultMapper<Category, CategoryDto> {
}
