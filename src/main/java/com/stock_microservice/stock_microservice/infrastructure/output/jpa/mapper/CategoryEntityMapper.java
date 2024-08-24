package com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper;

import com.stock_microservice.stock_microservice.domain.model.Category;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.entity.CategoryEntity;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {

    CategoryEntity toEntity(Category category);
    Category toCategory(CategoryEntity categoryEntity);
    List<Category> toCategoryList(List<CategoryEntity> categoryEntityList);
}