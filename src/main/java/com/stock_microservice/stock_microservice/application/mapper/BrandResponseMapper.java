package com.stock_microservice.stock_microservice.application.mapper;

import com.stock_microservice.stock_microservice.application.dto.BrandResponse;
import com.stock_microservice.stock_microservice.application.dto.CategoryResponse;
import com.stock_microservice.stock_microservice.domain.model.Brand;
import com.stock_microservice.stock_microservice.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandResponseMapper {
    BrandResponse toResponse(Brand brand);
    List<BrandResponse> toResponseList(List<Brand> brands);
}