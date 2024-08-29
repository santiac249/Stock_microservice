package com.stock_microservice.stock_microservice.application.mapper;

import com.stock_microservice.stock_microservice.application.dto.BrandRequest;
import com.stock_microservice.stock_microservice.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandRequestMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Brand toBrand(BrandRequest brandRequest);
}
