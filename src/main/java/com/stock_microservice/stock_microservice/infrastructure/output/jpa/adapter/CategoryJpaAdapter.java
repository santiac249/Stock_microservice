package com.stock_microservice.stock_microservice.infrastructure.output.jpa.adapter;

import com.stock_microservice.stock_microservice.domain.exception.DuplicateCategoryNameException;
import com.stock_microservice.stock_microservice.domain.model.Category;
import com.stock_microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import com.stock_microservice.stock_microservice.infrastructure.exception.NoDataFoundException;
import com.stock_microservice.stock_microservice.infrastructure.exception.CategoryNotFoundException;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.entity.CategoryEntity;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public List<Category> getAllCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        if(categoryEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.toCategoryList(categoryEntityList);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryEntityMapper.toCategory(categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryEntityMapper.toCategory(categoryRepository.findByName(name)
                .orElseThrow(CategoryNotFoundException::new));
    }

    @Override
    public void saveCategory(Category category) {
        if(categoryRepository.findByName(category.getName()).isPresent()){
            throw new DuplicateCategoryNameException(category.getName());
        }
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);
        categoryRepository.save(categoryEntity);
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void deleteCategoryByName(String name) {
        categoryRepository.deleteByName(name);
    }
}
