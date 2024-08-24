package com.stock_microservice.stock_microservice.domain.usecase;

import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;
import com.stock_microservice.stock_microservice.domain.exception.InvalidCategoryDataException;
import com.stock_microservice.stock_microservice.domain.model.Category;
import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUsecaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUsecase categoryUsecase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Electronics", "Devices and gadgets"));
        when(categoryPersistencePort.getAllCategories()).thenReturn(categories);

        List<Category> result = categoryUsecase.getAllCategories();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(categoryPersistencePort, times(1)).getAllCategories();
    }

    @Test
    void getCategoryById() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        when(categoryPersistencePort.getCategoryById(1L)).thenReturn(category);

        Category result = categoryUsecase.getCategoryById(1L);

        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        verify(categoryPersistencePort, times(1)).getCategoryById(1L);
    }

    @Test
    void getCategoryByName() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        when(categoryPersistencePort.getCategoryByName("Electronics")).thenReturn(category);

        Category result = categoryUsecase.getCategoryByName("Electronics");

        assertNotNull(result);
        assertEquals("Devices and gadgets", result.getDescription());
        verify(categoryPersistencePort, times(1)).getCategoryByName("Electronics");
    }

    @Test
    void saveCategory() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");

        categoryUsecase.saveCategory(category);

        verify(categoryPersistencePort, times(1)).saveCategory(category);
    }

    @Test
    void saveCategory_throwsExceptionWhenNameTooLong() {
        // Crea una categoría con un nombre que excede los 50 caracteres
        Category category = new Category();
        category.setName("Este nombre es demasiado largo y debería lanzar una excepcion");
        category.setDescription("Descripción válida");

        // Verifica que se lanza la excepción
        InvalidCategoryDataException exception = assertThrows(
                InvalidCategoryDataException.class,
                () -> categoryUsecase.saveCategory(category)
        );

        assertEquals("La categoria no puede tener más de 50 caracteres.", exception.getMessage());
    }


    @Test
    void saveCategory_throwsExceptionWhenDescriptionTooLong() {
        // Crea una categoría con una descripción de más de 90 caracteres
        Category category = new Category();
        category.setName("Valid Name");
        category.setDescription("Esta descripción es demasiado larga y debería provocar que se lance una excepción porque supera los 90 caracteres permitidos.");

        // Verifica que se lanza la excepción
        InvalidCategoryDataException exception = assertThrows(
                InvalidCategoryDataException.class,
                () -> categoryUsecase.saveCategory(category)
        );

        assertEquals("La descripción no puede tener más de 90 caracteres.", exception.getMessage());
    }

    @Test
    void updateCategory() {
        // Implementar lógica de actualización y su correspondiente prueba
    }



    @Test
    void deleteCategoryByName() {
        String categoryName = "Electronics";

        categoryUsecase.deleteCategoryByName(categoryName);

        verify(categoryPersistencePort, times(1)).deleteCategoryByName(categoryName);
    }

    @Test
    void getCategories() {
        // Datos de prueba
        PageRequestCustom pageRequest = new PageRequestCustom(0, 10, true, "name");
        PageCustom<Category> expectedPage = new PageCustom<>();

        // Agrega categorías a expectedPage si es necesario
        List<Category> mockCategories = Arrays.asList(
                new Category(1L, "Electronics", "Devices and gadgets"),
                new Category(2L, "Books", "Books and literature")
        );
        expectedPage.setContent(mockCategories);
        expectedPage.setTotalElements(2); // Asumiendo que hay 2 categorías en total
        expectedPage.setTotalPages(1); // Asumiendo que todas caben en una página

        // Configuración del comportamiento esperado
        when(categoryPersistencePort.getCategories(pageRequest)).thenReturn(expectedPage);

        // Ejecución del método
        PageCustom<Category> result = categoryUsecase.getCategories(pageRequest);

        // Verificación
        assertEquals(expectedPage, result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(mockCategories, result.getContent());
        verify(categoryPersistencePort).getCategories(pageRequest);
    }

    @Test
    void getCategories_throwsExceptionWhenNoCategoriesFound() {
        PageRequestCustom pageRequest = new PageRequestCustom(0, 10, true, "name");
        PageCustom<Category> emptyPage = new PageCustom<>(new ArrayList<>(), 0, 10, 1);

        System.out.println("emptyPage content: " + emptyPage.getContent());  // Añade esta línea

        when(categoryPersistencePort.getCategories(pageRequest)).thenReturn(emptyPage);

        InvalidCategoryDataException exception = assertThrows(
                InvalidCategoryDataException.class,
                () -> categoryUsecase.getCategories(pageRequest)
        );

        assertEquals("No se encontraron categorías.", exception.getMessage());
        verify(categoryPersistencePort).getCategories(pageRequest);
    }

}
