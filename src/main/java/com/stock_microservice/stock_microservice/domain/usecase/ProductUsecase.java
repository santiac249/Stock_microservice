package com.stock_microservice.stock_microservice.domain.usecase;

import com.stock_microservice.stock_microservice.domain.api.IProductServicePort;
import com.stock_microservice.stock_microservice.domain.model.Category;
import com.stock_microservice.stock_microservice.domain.model.Product;
import com.stock_microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import com.stock_microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import com.stock_microservice.stock_microservice.domain.spi.IProductPersistencePort;
import com.stock_microservice.stock_microservice.infrastructure.exception.BrandNotFoundException;
import com.stock_microservice.stock_microservice.domain.exception.CategoryValidationException;
import com.stock_microservice.stock_microservice.infrastructure.exception.CategoryNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductUsecase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;

    public ProductUsecase(IProductPersistencePort productPersistencePort, ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort) {
        this.productPersistencePort = productPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }


    @Override
    public void saveProduct(Product product) {
        // Validar que el producto tenga una marca asociada
        if (product.getBrand() == null) {
            throw new BrandNotFoundException("El producto debe tener una marca asociada.");
        }

        // Validar que la marca asociada al producto exista
        if (brandPersistencePort.getBrandById(product.getBrand().getId()) == null) {
            throw new BrandNotFoundException("No existe una marca con ID: " + product.getBrand().getId());
        }

        // Validar que el producto tenga entre 1 y 3 categorías asociadas
        List<Category> categories = product.getCategories();
        if (categories == null || categories.isEmpty()) {
            throw new CategoryValidationException("El producto debe tener al menos una categoría asociada.");
        }
        if (categories.size() > 3) {
            throw new CategoryValidationException("El producto no puede tener más de tres categorías asociadas.");
        }

        // Validar que no haya categorías repetidas
        Set<Category> uniqueCategories = new HashSet<>(categories);
        if (uniqueCategories.size() < categories.size()) {
            throw new CategoryValidationException("El producto no puede tener categorías repetidas.");
        }

        // Validar que las categorías asociadas existan
        for (Category category : categories) {
            if (categoryPersistencePort.getCategoryById(product.getBrand().getId()) == null) {
                throw new CategoryNotFoundException("No existe una categoria con ID: " + product.getBrand().getId());
            }
        }

        // Guardar el producto si todas las validaciones pasan
        productPersistencePort.saveProduct(product);
    }
}
