package com.stock_microservice.stock_microservice.domain.usecase;

import com.stock_microservice.stock_microservice.domain.exception.InvalidBrandDataException;
import com.stock_microservice.stock_microservice.domain.model.Brand;
import com.stock_microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

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
}
