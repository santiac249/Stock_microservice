package com.stock_microservice.stock_microservice.domain.usecase;

import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;
import com.stock_microservice.stock_microservice.domain.api.ICategoryServicePort;
import com.stock_microservice.stock_microservice.domain.exception.InvalidCategoryDataException;
import com.stock_microservice.stock_microservice.domain.model.Category;
import com.stock_microservice.stock_microservice.domain.spi.ICategoryPersistencePort;

import java.util.List;

public class CategoryUsecase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUsecase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }


    @Override
    public List<Category> getAllCategories() {
        return categoryPersistencePort.getAllCategories();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryPersistencePort.getCategoryById(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryPersistencePort.getCategoryByName(name);
    }

    @Override
    public void saveCategory(Category category) {
        if (category.getName().length() > 50 ) {
            throw new InvalidCategoryDataException("La categoria no puede tener más de 50 caracteres.");
        }

        // La descripción no debe exceder 90 caracteres
        if (category.getDescription().length() > 90) {
            throw new InvalidCategoryDataException("La descripción no puede tener más de 90 caracteres.");
        }
        categoryPersistencePort.saveCategory(category);

    }

    @Override
    public void updateCategory(Category category) {
        categoryPersistencePort.updateCategory(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryPersistencePort.deleteCategoryById(id);
    }

    @Override
    public void deleteCategoryByName(String name) {
        categoryPersistencePort.deleteCategoryByName(name);
    }

    @Override
    public PageCustom<Category> getCategories(PageRequestCustom pageRequest) {
        // Llamar al puerto de persistencia para obtener las categorías paginadas
        PageCustom<Category> categoriesPage = categoryPersistencePort.getCategories(pageRequest);

        // Validar que la respuesta no sea nula
        if (categoriesPage == null || categoriesPage.getContent().isEmpty()) {
            throw new InvalidCategoryDataException("No se encontraron categorías.");
        }

        return categoriesPage;
    }
}
