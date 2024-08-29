package com.stock_microservice.stock_microservice.infrastructure.input.rest;

import com.stock_microservice.stock_microservice.application.dto.BrandRequest;
import com.stock_microservice.stock_microservice.application.handler.IBrandHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@Tag(name = "Brand", description = "APIs relacionadas con marcas")
public class BrandRestController {

    private final IBrandHandler brandHandler;

    @Operation(summary = "Guardar una nueva marca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marca creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveBrand(@RequestBody BrandRequest brandRequest) {
        brandHandler.saveBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
