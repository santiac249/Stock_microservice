package com.stock_microservice.stock_microservice.application.handler;

import com.stock_microservice.stock_microservice.application.dto.BrandRequest;
import com.stock_microservice.stock_microservice.application.dto.BrandResponse;

import java.util.List;


public interface IBrandHandler {

    void saveBrand(BrandRequest brandRequest);
    List<BrandResponse> getAllBrands();
}
