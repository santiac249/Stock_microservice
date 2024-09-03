package com.stock_microservice.stock_microservice.infrastructure.input.rest;

import com.stock_microservice.stock_microservice.application.dto.BrandRequest;
import com.stock_microservice.stock_microservice.application.dto.BrandResponse;
import com.stock_microservice.stock_microservice.application.handler.IBrandHandler;
import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "Obtener una marca por el ID")
    @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandResponse.class))})
    @GetMapping("/id/{id}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable Long id){
        return ResponseEntity.ok(brandHandler.getBrandById(id));
    }

    @Operation(summary = "Obtener una marca por el nombre")
    @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandResponse.class))})
    @GetMapping("/name/{name}")
    public ResponseEntity<BrandResponse> getBrandByName(@PathVariable String name){
        return ResponseEntity.ok(brandHandler.getBrandByName(name));
    }

    @Operation(summary = "Obtener todas las marcas")
    @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandResponse.class))})
    @GetMapping("/")
    public ResponseEntity<List<BrandResponse>> getAllBrands(){
        return ResponseEntity.ok(brandHandler.getAllBrands());
    }

    @Operation(summary = "Obtener marcas paginadas",
            description = "Devuelve una lista de marcas paginadas según el número de página, tamaño y orden de clasificación especificados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageCustom.class))}),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontraron marcas", content = @Content)
    })
    @GetMapping("/paged/{page}/{size}/{sortOrder}")
    public ResponseEntity<PageCustom<BrandResponse>> getBrandsPaged(
            @PathVariable int page,
            @PathVariable int size,
            @PathVariable String sortOrder) {

        // Divide el sortOrder en campo y orden
        String[] sort = sortOrder.split(","); // dividir en campo y orden
        String sortField = sort[0]; // campo para ordenar
        boolean ascending = sort.length > 1 && sort[1].equalsIgnoreCase("asc");

        PageRequestCustom pageRequest = new PageRequestCustom(page, size, ascending, sortField);
        PageCustom<BrandResponse> brandsPage = brandHandler.getBrands(pageRequest);
        return ResponseEntity.ok(brandsPage);
    }
}
