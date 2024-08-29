package com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper;

import com.stock_microservice.stock_microservice.domain.model.Brand;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.entity.BrandEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandEntityMapper {
    BrandEntity toEntity(Brand brand);
    Brand toBrand(BrandEntity brandEntity);
}
