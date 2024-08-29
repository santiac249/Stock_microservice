package com.stock_microservice.stock_microservice.application.mapper;

import com.stock_microservice.stock_microservice.application.dto.CategoryResponse;
import com.stock_microservice.stock_microservice.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {

    CategoryResponse toResponse(Category category);
    List<CategoryResponse> toResponseList(List<Category> categories);
}