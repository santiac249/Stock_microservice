package com.stock_microservice.stock_microservice.application.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private double price;
    private List<CategoryResponse> categories;
    private BrandResponse brand;
}
