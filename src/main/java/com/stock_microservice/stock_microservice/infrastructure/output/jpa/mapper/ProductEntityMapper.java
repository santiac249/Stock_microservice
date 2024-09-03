package com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper;

import com.stock_microservice.stock_microservice.domain.model.Product;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductEntityMapper {

    ProductEntity toEntity(Product product);

    Product toProduct(ProductEntity productEntity);
}

