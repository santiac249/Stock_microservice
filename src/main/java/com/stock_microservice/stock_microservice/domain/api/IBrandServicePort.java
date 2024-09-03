package com.stock_microservice.stock_microservice.domain.api;

import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;
import com.stock_microservice.stock_microservice.domain.model.Brand;

import java.util.List;


public interface IBrandServicePort {
    void saveBrand(Brand brand);
    List<Brand> getAllBrands();
    Brand getBrandById(Long id);
    Brand getBrandByName(String name);
    PageCustom<Brand> getBrands(PageRequestCustom pageRequest);


}
