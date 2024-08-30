package com.stock_microservice.stock_microservice.infrastructure.input.rest;

import java.util.List;

import com.stock_microservice.stock_microservice.application.dto.CategoryRequest;
import com.stock_microservice.stock_microservice.application.dto.CategoryResponse;
import com.stock_microservice.stock_microservice.application.handler.ICategoryHandler;
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

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "APIs relacionadas con categorías")
public class CategoryRestController {

    private final ICategoryHandler categoryHandler;

    @Operation(summary = "Guardar una nueva categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveCategory(@RequestBody CategoryRequest categoryRequest){
        categoryHandler.saveCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Obtener todas las categorías")
    @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))})
    @GetMapping("/")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        return ResponseEntity.ok(categoryHandler.getAllCategories());
    }

    @Operation(summary = "Obtener una categoría por ID")
    @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))})
    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryHandler.getCategoryById(id));
    }

    @Operation(summary = "Obtener una categoría por nombre")
    @ApiResponse(responseCode = "200", description = "Operación exitosa",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))})
    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable String name){
        return ResponseEntity.ok(categoryHandler.getCategoryByName(name));
    }

    @Operation(summary = "Actualizar una categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    @PutMapping("/")
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryRequest categoryRequest){
        categoryHandler.updateCategory(categoryRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Eliminar una categoría por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content)
    })
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id){
        categoryHandler.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Eliminar una categoría por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content)
    })
    @DeleteMapping("/name/{name}")
    public ResponseEntity<Void> deleteCategoryByName(@PathVariable String name){
        categoryHandler.deleteCategoryByName(name);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener categorías paginadas",
            description = "Devuelve una lista de categorías paginadas según el número de página, tamaño y orden de clasificación especificados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageCustom.class))}),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontraron categorías", content = @Content)
    })
    @GetMapping("/paged/{page}/{size}/{sortOrder}")
    public ResponseEntity<PageCustom<CategoryResponse>> getCategoriesPaged(
            @PathVariable int page,
            @PathVariable int size,
            @PathVariable String sortOrder) {

        // Divide el sortOrder en campo y orden
        String[] sort = sortOrder.split(","); // dividir en campo y orden
        String sortField = sort[0]; // campo para ordenar
        boolean ascending = sort.length > 1 && sort[1].equalsIgnoreCase("asc");

        PageRequestCustom pageRequest = new PageRequestCustom(page, size, ascending, sortField);
        PageCustom<CategoryResponse> categoriesPage = categoryHandler.getCategories(pageRequest);
        return ResponseEntity.ok(categoriesPage);
    }
}

