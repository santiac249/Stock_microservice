package com.stock_microservice.stock_microservice.application.handler;

import com.stock_microservice.stock_microservice.application.dto.ProductRequest;
import com.stock_microservice.stock_microservice.application.dto.ProductResponse;
import com.stock_microservice.stock_microservice.application.mapper.ProductRequestMapper;
import com.stock_microservice.stock_microservice.application.mapper.ProductResponseMapper;
import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;
import com.stock_microservice.stock_microservice.domain.api.IBrandServicePort;
import com.stock_microservice.stock_microservice.domain.api.ICategoryServicePort;
import com.stock_microservice.stock_microservice.domain.api.IProductServicePort;
import com.stock_microservice.stock_microservice.domain.model.Brand;
import com.stock_microservice.stock_microservice.domain.model.Category;
import com.stock_microservice.stock_microservice.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductHandler implements IProductHandler {

    private final IProductServicePort productServicePort;
    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;
    private final IBrandServicePort brandServicePort;
    private final ICategoryServicePort categoryServicePort;

    @Override
    public void saveProduct(ProductRequest productRequest) {

        // Convertir el brand_id en una entidad Brand
        Brand brand = brandServicePort.getBrandById(productRequest.getBrand_id());

        // Convertir la lista de category_id en una lista de entidades Category
        List<Category> categories = productRequest.getCategory_id().stream()

                .map(category_id -> {
                    Category category = categoryServicePort.getCategoryById(category_id);
                    return category;
                }).toList();


        Product product = productRequestMapper.toProduct(productRequest, brand, categories);

        // Guardar el producto
        productServicePort.saveProduct(product);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productServicePort.getProductById(id);
        return productResponseMapper.toResponse(product);
    }

    @Override
    public ProductResponse getProductByName(String name) {
        Product product = productServicePort.getProductByName(name);
        return productResponseMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productResponseMapper.toResponseList(productServicePort.getAllProducts());
    }

    @Override
    public PageCustom<ProductResponse> getProducts(PageRequestCustom pageRequest, String brand, String category) {
        PageCustom<Product> productsPage = productServicePort.getProducts(pageRequest, brand, category);

        List<ProductResponse> responseList = productResponseMapper.toResponseList(productsPage.getContent());
        return new PageCustom<>(
                responseList,
                productsPage.getTotalElements(),
                productsPage.getTotalPages(),
                productsPage.getCurrentPage(),
                productsPage.isAscending()
        );
    }

}