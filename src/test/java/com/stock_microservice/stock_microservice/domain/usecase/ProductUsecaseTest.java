package com.stock_microservice.stock_microservice.domain.usecase;

import com.stock_microservice.stock_microservice.domain.model.Brand;
import com.stock_microservice.stock_microservice.domain.model.Category;
import com.stock_microservice.stock_microservice.domain.model.Product;
import com.stock_microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import com.stock_microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import com.stock_microservice.stock_microservice.domain.spi.IProductPersistencePort;
import com.stock_microservice.stock_microservice.infrastructure.exception.BrandNotFoundException;
import com.stock_microservice.stock_microservice.domain.exception.CategoryValidationException;
import com.stock_microservice.stock_microservice.infrastructure.exception.CategoryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductUsecaseTest {

    @Mock
    private IProductPersistencePort productPersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private ProductUsecase productUsecase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveProduct_ShouldThrowBrandNotFoundException_WhenBrandIsNull() {
        Product product = new Product();
        product.setBrand(null);

        assertThrows(BrandNotFoundException.class, () -> productUsecase.saveProduct(product));

        verify(brandPersistencePort, never()).getBrandById(any());
        verify(productPersistencePort, never()).saveProduct(any());
    }

    @Test
    void saveProduct_ShouldThrowBrandNotFoundException_WhenBrandDoesNotExist() {
        Product product = new Product();
        Brand brand = new Brand();
        brand.setId(1L);
        product.setBrand(brand);

        when(brandPersistencePort.getBrandById(1L)).thenReturn(null);

        assertThrows(BrandNotFoundException.class, () -> productUsecase.saveProduct(product));

        verify(brandPersistencePort).getBrandById(1L);
        verify(productPersistencePort, never()).saveProduct(any());
    }

    @Test
    void saveProduct_ShouldThrowCategoryValidationException_WhenNoCategoriesAreAssociated() {
        Product product = new Product();
        Brand brand = new Brand();
        brand.setId(1L);
        product.setBrand(brand);
        product.setCategories(Collections.emptyList());

        when(brandPersistencePort.getBrandById(1L)).thenReturn(brand);

        assertThrows(CategoryValidationException.class, () -> productUsecase.saveProduct(product));

        verify(brandPersistencePort).getBrandById(1L);
        verify(productPersistencePort, never()).saveProduct(any());
    }

    @Test
    void saveProduct_ShouldThrowCategoryValidationException_WhenMoreThanThreeCategoriesAreAssociated() {
        Product product = new Product();
        Brand brand = new Brand();
        brand.setId(1L);
        product.setBrand(brand);
        product.setCategories(Arrays.asList(new Category(), new Category(), new Category(), new Category()));

        when(brandPersistencePort.getBrandById(1L)).thenReturn(brand);

        assertThrows(CategoryValidationException.class, () -> productUsecase.saveProduct(product));

        verify(brandPersistencePort).getBrandById(1L);
        verify(productPersistencePort, never()).saveProduct(any());
    }

    @Test
    void saveProduct_ShouldThrowCategoryValidationException_WhenCategoriesAreDuplicated() {
        Product product = new Product();
        Brand brand = new Brand();
        brand.setId(1L);
        product.setBrand(brand);
        Category category = new Category();
        category.setId(1L);
        product.setCategories(Arrays.asList(category, category));

        when(brandPersistencePort.getBrandById(1L)).thenReturn(brand);

        assertThrows(CategoryValidationException.class, () -> productUsecase.saveProduct(product));

        verify(brandPersistencePort).getBrandById(1L);
        verify(productPersistencePort, never()).saveProduct(any());
    }

    @Test
    void saveProduct_ShouldThrowCategoryNotFoundException_WhenCategoryDoesNotExist() {
        Product product = new Product();
        Brand brand = new Brand();
        brand.setId(1L);
        product.setBrand(brand);
        Category category = new Category();
        category.setId(1L);
        product.setCategories(Collections.singletonList(category));

        when(brandPersistencePort.getBrandById(1L)).thenReturn(brand);
        when(categoryPersistencePort.getCategoryById(1L)).thenReturn(null);

        assertThrows(CategoryNotFoundException.class, () -> productUsecase.saveProduct(product));

        verify(brandPersistencePort).getBrandById(1L);
        verify(categoryPersistencePort).getCategoryById(1L);
        verify(productPersistencePort, never()).saveProduct(any());
    }

    @Test
    void saveProduct_ShouldSaveProduct_WhenAllValidationsPass() {
        Product product = new Product();
        Brand brand = new Brand();
        brand.setId(1L);
        product.setBrand(brand);
        Category category = new Category();
        category.setId(1L);
        product.setCategories(Collections.singletonList(category));

        when(brandPersistencePort.getBrandById(1L)).thenReturn(brand);
        when(categoryPersistencePort.getCategoryById(1L)).thenReturn(category);

        productUsecase.saveProduct(product);

        verify(brandPersistencePort).getBrandById(1L);
        verify(categoryPersistencePort).getCategoryById(1L);
        verify(productPersistencePort).saveProduct(product);
    }
}
