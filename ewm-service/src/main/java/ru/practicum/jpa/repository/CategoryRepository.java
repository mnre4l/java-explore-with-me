package ru.practicum.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.jpa.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
