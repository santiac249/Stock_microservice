package com.stock_microservice.stock_microservice.application.handler;

import com.stock_microservice.stock_microservice.application.dto.BrandRequest;
import com.stock_microservice.stock_microservice.application.dto.BrandResponse;
import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;

import java.util.List;


public interface IBrandHandler {

    void saveBrand(BrandRequest brandRequest);
    BrandResponse getBrandById(Long id);
    BrandResponse getBrandByName(String name);
    List<BrandResponse> getAllBrands();
    PageCustom<BrandResponse> getBrands(PageRequestCustom pageRequest);
}
