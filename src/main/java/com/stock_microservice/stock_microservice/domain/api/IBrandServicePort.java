package com.stock_microservice.stock_microservice.domain.api;

import com.stock_microservice.stock_microservice.domain.model.Brand;

import java.util.List;


public interface IBrandServicePort {
    void saveBrand(Brand brand);
    List<Brand> getAllBrands();

}
