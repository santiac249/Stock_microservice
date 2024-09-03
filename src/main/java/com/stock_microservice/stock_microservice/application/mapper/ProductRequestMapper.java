package com.stock_microservice.stock_microservice.application.mapper;

import com.stock_microservice.stock_microservice.application.dto.ProductRequest;
import com.stock_microservice.stock_microservice.domain.model.Brand;
import com.stock_microservice.stock_microservice.domain.model.Category;
import com.stock_microservice.stock_microservice.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {
    @Mappings({
            @Mapping(target = "name", source = "productRequest.name"),
            @Mapping(target = "description", source = "productRequest.description"),
            @Mapping(target = "quantity", source = "productRequest.quantity"),
            @Mapping(target = "price", source = "productRequest.price"),
            @Mapping(target = "brand", source = "brand"),
            @Mapping(target = "categories", source = "categories")
    })
    Product toProduct(ProductRequest productRequest, Brand brand, List<Category> categories);
}
