package com.stock_microservice.stock_microservice.application.handler;

import com.stock_microservice.stock_microservice.application.dto.CategoryRequest;
import com.stock_microservice.stock_microservice.application.dto.CategoryResponse;
import com.stock_microservice.stock_microservice.application.mapper.CategoryRequestMapper;
import com.stock_microservice.stock_microservice.application.mapper.CategoryResponseMapper;
import com.stock_microservice.stock_microservice.domain.api.ICategoryServicePort;
import com.stock_microservice.stock_microservice.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;


    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryResponseMapper.toResponseList(categoryServicePort.getAllCategories());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryServicePort.getCategoryById(id);
        return categoryResponseMapper.toResponse(category);
    }

    @Override
    public CategoryResponse getCategoryByName(String name) {
        Category category = categoryServicePort.getCategoryByName(name);
        return categoryResponseMapper.toResponse(category);
    }

    @Override
    public void saveCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.saveCategory(category);
    }

    @Override
    public void updateCategory(CategoryRequest categoryRequest) {
        Category oldCategory = categoryServicePort.getCategoryById(categoryRequest.getId());
        Category newCategory = categoryRequestMapper.toCategory(categoryRequest);
        newCategory.setId(oldCategory.getId());
        newCategory.setName(oldCategory.getName());
        newCategory.setDescription(oldCategory.getDescription());
        categoryServicePort.updateCategory(newCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = categoryServicePort.getCategoryById(id);
        categoryServicePort.deleteCategoryById(id);
    }

    @Override
    public void deleteCategoryByName(String name) {
        Category category = categoryServicePort.getCategoryByName(name);
        categoryServicePort.deleteCategoryByName(name);

    }
}
