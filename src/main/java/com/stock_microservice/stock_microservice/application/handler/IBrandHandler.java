package com.stock_microservice.stock_microservice.application.handler;

import com.stock_microservice.stock_microservice.application.dto.BrandRequest;


public interface IBrandHandler {

    void saveBrand(BrandRequest brandRequest);

}
