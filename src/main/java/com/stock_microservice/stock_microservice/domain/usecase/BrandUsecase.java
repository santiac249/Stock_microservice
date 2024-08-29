package com.stock_microservice.stock_microservice.domain.usecase;

import com.stock_microservice.stock_microservice.domain.api.IBrandServicePort;
import com.stock_microservice.stock_microservice.domain.exception.InvalidBrandDataException;
import com.stock_microservice.stock_microservice.domain.model.Brand;
import com.stock_microservice.stock_microservice.domain.model.Category;
import com.stock_microservice.stock_microservice.domain.spi.IBrandPersistencePort;

import java.util.List;

public class BrandUsecase implements IBrandServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUsecase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveBrand(Brand brand) {
        if (brand.getName().length() > 50 ) {
            throw new InvalidBrandDataException("La marca no puede contener más de 50 caracteres.");
        }

        // La descripción no debe exceder los 120 caracteres
        if (brand.getDescription().length() > 120) {
            throw new InvalidBrandDataException("La descripción no puede contener más de 120 caracteres.");
        }
        brandPersistencePort.saveBrand(brand);
    }

    @Override
    public List<Brand> getAllBrands() {
        return List.of();
    }
}
