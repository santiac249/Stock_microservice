package com.stock_microservice.stock_microservice.application.mapper;

import com.stock_microservice.stock_microservice.application.dto.ProductResponse;
import com.stock_microservice.stock_microservice.domain.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {
    ProductResponse toResponse(Product product);
    List<ProductResponse> toResponseList(List<Product> products);
}
