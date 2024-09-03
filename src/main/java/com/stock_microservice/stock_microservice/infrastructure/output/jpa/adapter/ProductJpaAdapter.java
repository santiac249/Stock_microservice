package com.stock_microservice.stock_microservice.infrastructure.output.jpa.adapter;

import com.stock_microservice.stock_microservice.domain.exception.DuplicateProductNameException;
import com.stock_microservice.stock_microservice.domain.model.Product;
import com.stock_microservice.stock_microservice.domain.spi.IProductPersistencePort;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.entity.ProductEntity;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper.ProductEntityMapper;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public void saveProduct(Product product) {
        if(productRepository.findByName(product.getName()).isPresent()){
            throw new DuplicateProductNameException("Ya existe un producto con este nombre: "+product.getName());
        }
        ProductEntity productEntity = productEntityMapper.toEntity(product);
        productRepository.save(productEntity);
    }
}
