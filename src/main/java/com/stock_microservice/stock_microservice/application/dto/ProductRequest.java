package com.stock_microservice.stock_microservice.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class ProductRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Min(1)
    private Integer quantity;

    @NotBlank
    @Positive(message = "El precio debe ser mayor a 0")
    private double price;

    @NotNull
    @Size(min = 1, max = 3, message = "Debe tener al menos una categoria y maximo 3")
    private List<Long> category_id;

    @NotNull
    private Long brand_id;
}
