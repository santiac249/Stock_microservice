package com.stock_microservice.stock_microservice.application.mapper;

import com.stock_microservice.stock_microservice.application.dto.CategoryRequest;
import com.stock_microservice.stock_microservice.domain.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {
    Category toCategory(CategoryRequest categoryRequest);

}

