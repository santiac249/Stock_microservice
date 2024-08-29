package com.stock_microservice.stock_microservice.domain.spi;

import com.stock_microservice.stock_microservice.domain.model.Brand;

public interface IBrandPersistencePort {

    void saveBrand (Brand brand);
}
