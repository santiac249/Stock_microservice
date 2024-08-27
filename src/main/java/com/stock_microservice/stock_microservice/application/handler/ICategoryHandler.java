package com.stock_microservice.stock_microservice.application.handler;

import com.stock_microservice.stock_microservice.application.dto.CategoryRequest;
import com.stock_microservice.stock_microservice.application.dto.CategoryResponse;
import com.stock_microservice.stock_microservice.domain.Pagination.*;

import java.util.List;

public interface ICategoryHandler {

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse getCategoryByName(String nombre);

    void saveCategory(CategoryRequest categoryRequest);

    void updateCategory(CategoryRequest categoryRequest);

    void deleteCategoryById(Long id);

    void deleteCategoryByName(String nombre);

    PageCustom<CategoryResponse> getCategories(PageRequestCustom pageRequest);
}