package com.stock_microservice.stock_microservice.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
    @NotBlank
    @Size(min = 2, max = 90)
    private String description;
}