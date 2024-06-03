package com.tai.essence.service.category;

import com.tai.essence.dto.CategoryDTO;
import com.tai.essence.entity.Category;
import com.tai.essence.exception.DuplicateNameException;
import com.tai.essence.helper.mapping.CategoryMapping;
import com.tai.essence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final CategoryMapping categoryMapping;
    @Override
    public List<Category> findAll() {
        log.info("Get all category");
        List<CategoryDTO> categoryDTOS = categoryRepository.findAll().stream().map(categoryMapping::entityToDto).toList();
        log.info(String.valueOf(categoryDTOS.size()));
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        log.info("Finding category with id: " + id);
        return categoryRepository.findById(id).orElseThrow(null);
    }

    @Override
    public boolean insert(Category dto) throws DuplicateNameException {
        Optional<Category> category = categoryRepository.findByName(dto.getName());
        if(category.isEmpty()){
            try {
                Category savedCategory = new Category();
                savedCategory.setName(dto.getName());
                savedCategory.setLevel(dto.getLevel());
                savedCategory.setParentCategory(dto.getParentCategory());

                categoryRepository.save(savedCategory);
                log.info("Insert category");
            }catch (Exception e){
                log.error("Error");
                throw new RuntimeException("Error saving category", e);
            }
        }else {
            log.error("Brand error duplicate name");
            throw new DuplicateNameException("Brand name already exist");
        }
        return false;
    }

    @Override
    public boolean update(Long id, Category dto) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()){
            try {
                categoryRepository.delete(category.get());
                return true;
            }catch (DataAccessException e){
                log.error("Error deleting category: " + e.getMessage());
            } catch (Exception e) {
                log.error("Unexpected error deleting category", e);
            }

        }else {
            log.error("Category with id " + id + " not found");
        }
        return false;
    }
}
