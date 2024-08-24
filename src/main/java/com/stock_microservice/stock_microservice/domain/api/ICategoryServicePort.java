package com.stock_microservice.stock_microservice.domain.api;

import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;
import com.stock_microservice.stock_microservice.domain.model.Category;

import java.util.List;

public interface ICategoryServicePort {
    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    void saveCategory(Category category);

    void updateCategory(Category category);

    void deleteCategoryById(Long id);

    void deleteCategoryByName(String name);

    PageCustom<Category> getCategories(PageRequestCustom pageRequest);
}
