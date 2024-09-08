package com.stock_microservice.stock_microservice.domain.api;

import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;
import com.stock_microservice.stock_microservice.domain.model.Product;

import java.util.List;

public interface IProductServicePort {
    void saveProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product getProductByName(String name);
    PageCustom<Product> getProducts(PageRequestCustom pageRequest, String brand, String category);
}
