package com.stock_microservice.stock_microservice.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponse {
    private Long id;
    private String name;
    private String description;

}
