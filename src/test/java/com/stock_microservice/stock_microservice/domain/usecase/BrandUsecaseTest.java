package com.stock_microservice.stock_microservice.domain.usecase;

import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;
import com.stock_microservice.stock_microservice.domain.exception.InvalidBrandDataException;
import com.stock_microservice.stock_microservice.domain.model.Brand;
import com.stock_microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BrandUsecaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUsecase brandUsecase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveBrand_WhenNameIsTooLong_ShouldThrowInvalidCategoryDataException() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("Marca con un nombre demasiado largo que supera los cincuenta caracteres");
        brand.setDescription("Descripción válida");

        // Act & Assert
        assertThrows(InvalidBrandDataException.class, () -> brandUsecase.saveBrand(brand));

        // Verificar que el método saveBrand no se llama en caso de error
        verify(brandPersistencePort, never()).saveBrand(brand);
    }

    @Test
    public void testSaveBrand_WhenDescriptionIsTooLong_ShouldThrowInvalidCategoryDataException() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("Nombre válido");
        brand.setDescription("Descripción demasiado larga que supera los ciento veinte caracteres. Esta es una descripción sumamente larga que no debería ser permitida en el sistema según las reglas de negocio.");

        // Act & Assert
        assertThrows(InvalidBrandDataException.class, () -> brandUsecase.saveBrand(brand));

        // Verificar que el método saveBrand no se llama en caso de error
        verify(brandPersistencePort, never()).saveBrand(brand);
    }

    @Test
    public void testSaveBrand_WhenDataIsValid_ShouldCallPersistencePort() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("Nombre válido");
        brand.setDescription("Descripción válida");

        // Act
        brandUsecase.saveBrand(brand);

        // Assert
        verify(brandPersistencePort).saveBrand(brand);
    }

    @Test
    void testGetAllBrands_ShouldReturnListOfBrands() {
        // Arrange
        Brand brand1 = new Brand(1L, "Marca1", "Descripción1");
        Brand brand2 = new Brand(2L, "Marca2", "Descripción2");
        when(brandPersistencePort.getAllBrands()).thenReturn(Arrays.asList(brand1, brand2));

        // Act
        List<Brand> result = brandUsecase.getAllBrands();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(brand1));
        assertTrue(result.contains(brand2));
    }

    @Test
    void testGetBrandById_ShouldReturnBrand() {
        // Arrange
        Long brandId = 1L;
        Brand brand = new Brand(brandId, "Marca1", "Descripción1");
        when(brandPersistencePort.getBrandById(brandId)).thenReturn(brand);

        // Act
        Brand result = brandUsecase.getBrandById(brandId);

        // Assert
        assertNotNull(result);
        assertEquals(brandId, result.getId());
        assertEquals("Marca1", result.getName());
        assertEquals("Descripción1", result.getDescription());
    }

    @Test
    void testGetBrandByName_ShouldReturnBrand() {
        // Arrange
        String brandName = "Marca1";
        Brand brand = new Brand(1L, brandName, "Descripción1");
        when(brandPersistencePort.getBrandByName(brandName)).thenReturn(brand);

        // Act
        Brand result = brandUsecase.getBrandByName(brandName);

        // Assert
        assertNotNull(result);
        assertEquals(brandName, result.getName());
        assertEquals("Descripción1", result.getDescription());
    }

    @Test
    void testGetBrands_WhenBrandsPageIsEmpty_ShouldThrowInvalidBrandDataException() {
        // Arrange
        PageRequestCustom pageRequest = new PageRequestCustom();
        when(brandPersistencePort.getBrands(pageRequest)).thenReturn(new PageCustom<>(Collections.emptyList(), 0, 0, 0));

        // Act & Assert
        assertThrows(InvalidBrandDataException.class, () -> brandUsecase.getBrands(pageRequest));
    }

    @Test
    void testGetBrands_ShouldReturnPagedBrands() {
        // Arrange
        PageRequestCustom pageRequest = new PageRequestCustom();
        Brand brand1 = new Brand(1L, "Marca1", "Descripción1");
        Brand brand2 = new Brand(2L, "Marca2", "Descripción2");
        PageCustom<Brand> brandsPage = new PageCustom<>(Arrays.asList(brand1, brand2), 1, 2, 2);
        when(brandPersistencePort.getBrands(pageRequest)).thenReturn(brandsPage);

        // Act
        PageCustom<Brand> result = brandUsecase.getBrands(pageRequest);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().contains(brand1));
        assertTrue(result.getContent().contains(brand2));
    }
}