package com.stock_microservice.stock_microservice.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;
}
