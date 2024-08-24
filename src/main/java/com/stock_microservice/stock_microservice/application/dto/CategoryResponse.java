package com.stock_microservice.stock_microservice.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    private long id;
    private String name;
    private String description;
}

