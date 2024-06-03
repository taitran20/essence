package com.tai.essence.helper.mapping;

import com.tai.essence.dto.CategoryDTO;
import com.tai.essence.entity.Category;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class CategoryMapping implements BaseMapping<Category, CategoryDTO>{
    @Override
    public CategoryDTO entityToDto(Category entity) {
        final var parentCategory = Optional.ofNullable(entity.getParentCategory())
                .orElseGet(Category::new);
        return CategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .level(entity.getLevel())
                .parentCategoryDTO(
                        CategoryDTO.builder()
                                .id(parentCategory.getId())
                                .name(parentCategory.getName())
                                .level(parentCategory.getLevel())
                                .build()
                )
                .build();
    }

    @Override
    public Category dtoToEntity(CategoryDTO dto) {
        final var parentCategoryDTO = Optional.ofNullable(dto.getParentCategoryDTO())
                .orElseGet(CategoryDTO::new);
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .level(dto.getLevel())
                .parentCategory(
                        Category.builder()
                                .id(parentCategoryDTO.getId())
                                .name(parentCategoryDTO.getName())
                                .level(parentCategoryDTO.getLevel())
                                .build()
                )
                .build();
    }
}
