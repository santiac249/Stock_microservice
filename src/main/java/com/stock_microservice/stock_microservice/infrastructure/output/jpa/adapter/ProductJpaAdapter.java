package com.stock_microservice.stock_microservice.infrastructure.output.jpa.adapter;

import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;
import com.stock_microservice.stock_microservice.domain.exception.DuplicateProductNameException;
import com.stock_microservice.stock_microservice.domain.model.Product;
import com.stock_microservice.stock_microservice.domain.spi.IProductPersistencePort;
import com.stock_microservice.stock_microservice.infrastructure.exception.ProductNotFoundException;
import com.stock_microservice.stock_microservice.infrastructure.exception.NoDataFoundException;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.entity.ProductEntity;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper.ProductEntityMapper;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

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

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        if(productEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return productEntityMapper.toProductList(productEntityList);
    }

    @Override
    public Product getProductById(Long id) {
        return productEntityMapper.toProduct(productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new));
    }

    @Override
    public Product getProductByName(String name) {
        return productEntityMapper.toProduct(productRepository.findByName(name)
                .orElseThrow(ProductNotFoundException::new));
    }

    @Override
    public PageCustom<Product> getProducts(PageRequestCustom pageRequest) {
        PageRequest pageRequestSpring = PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getSize(),
                pageRequest.isAscending() ? Sort.by(pageRequest.getSortField()).ascending() : Sort.by(pageRequest.getSortField()).descending()
        );

        Page<ProductEntity> productEntityPage = productRepository.findAll(pageRequestSpring);

        List<Product> products = productEntityMapper.toProductList(productEntityPage.getContent());

        return new PageCustom<>(
                products,
                (int) productEntityPage.getTotalElements(),
                productEntityPage.getTotalPages(),
                productEntityPage.getNumber(),
                pageRequest.isAscending()
        );
    }
}

