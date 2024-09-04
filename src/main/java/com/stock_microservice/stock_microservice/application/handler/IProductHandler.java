package com.stock_microservice.stock_microservice.application.handler;

import com.stock_microservice.stock_microservice.application.dto.ProductRequest;
import com.stock_microservice.stock_microservice.application.dto.ProductResponse;
import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;

import java.util.List;

public interface IProductHandler {
    void saveProduct(ProductRequest productRequest);
    ProductResponse getProductById(Long id);
    ProductResponse getProductByName(String name);
    List<ProductResponse> getAllProducts();
    PageCustom<ProductResponse> getProducts(PageRequestCustom pageRequest);
}
