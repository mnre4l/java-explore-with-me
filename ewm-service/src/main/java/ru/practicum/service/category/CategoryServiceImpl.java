package ru.practicum.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.practicum.api.category.CategoryDto;
import ru.practicum.api.category.NewCategoryDto;
import ru.practicum.api.page.Page;
import ru.practicum.exception.DeleteCategoryException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.jpa.entity.Category;
import ru.practicum.jpa.repository.CategoryRepository;
import ru.practicum.jpa.repository.EventRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;


    @Override
    public void delete(final Long catId) {
        Optional.of(catId)
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Не найдена категория id = " + id)))
                .map(eventRepository::findAllByCategory)
                .filter(List::isEmpty)
                .orElseThrow(() -> new DeleteCategoryException("Есть связанные события с категорией catId = " + catId));
        categoryRepository.deleteById(catId);
    }


    @Override
    public CategoryDto create(NewCategoryDto crt) {
        Category category = Category.builder()
                .name(crt.getName())
                .build();

        categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDto update(Long id, NewCategoryDto updateCategory) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Не найдена категория id = " + id));

        handleNameUpdate(category, updateCategory.getName());
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public Collection<CategoryDto> getAll(Integer from, Integer size) {
        List<Category> categories = categoryRepository.findAll(new Page(from, size, Sort.unsorted()))
                .getContent();

        return categoryMapper.toDTO(categories);
    }

    @Override
    public CategoryDto get(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Не найдена категория id = " + id));
    }


    private void handleNameUpdate(Category category, @Nullable String newCategoryName) {
        Optional.ofNullable(newCategoryName)
                .filter(name -> !name.isBlank())
                .ifPresent(category::setName);
    }
}
