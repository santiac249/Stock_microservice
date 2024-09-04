package com.stock_microservice.stock_microservice.infrastructure.input.rest;

import com.stock_microservice.stock_microservice.application.dto.ProductRequest;
import com.stock_microservice.stock_microservice.application.dto.ProductResponse;
import com.stock_microservice.stock_microservice.application.handler.IProductHandler;
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
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "APIs relacionadas con productos")
public class ProductRestController {

    private final IProductHandler productHandler;

    @Operation(summary = "Guardar un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveProduct(@RequestBody ProductRequest productRequest) {
        productHandler.saveProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Obtener un producto por el ID")
    @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))})
    @GetMapping("/id/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productHandler.getProductById(id));
    }

    @Operation(summary = "Obtener un producto por el nombre")
    @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))})
    @GetMapping("/name/{name}")
    public ResponseEntity<ProductResponse> getProductByName(@PathVariable String name){
        return ResponseEntity.ok(productHandler.getProductByName(name));
    }

    @Operation(summary = "Obtener todos los productos")
    @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))})
    @GetMapping("/")
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        return ResponseEntity.ok(productHandler.getAllProducts());
    }

    @Operation(summary = "Obtener productos paginadas",
            description = "Devuelve una lista de productos paginadas según el número de página, tamaño y orden de clasificación especificados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageCustom.class))}),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontraron marcas", content = @Content)
    })
    @GetMapping("/paged/{page}/{size}/{sortOrder}")
    public ResponseEntity<PageCustom<ProductResponse>> getProductsPaged(
            @PathVariable int page,
            @PathVariable int size,
            @PathVariable String sortOrder) {

        // Divide el sortOrder en campo y orden
        String[] sort = sortOrder.split(","); // dividir en campo y orden
        String sortField = sort[0]; // campo para ordenar
        boolean ascending = sort.length > 1 && sort[1].equalsIgnoreCase("asc");

        PageRequestCustom pageRequest = new PageRequestCustom(page, size, ascending, sortField);
        PageCustom<ProductResponse> productsPage = productHandler.getProducts(pageRequest);
        return ResponseEntity.ok(productsPage);
    }
}
