package com.stock_microservice.stock_microservice.domain.api;

import com.stock_microservice.stock_microservice.domain.model.Brand;


public interface IBrandServicePort {
    void saveBrand(Brand brand);
}
