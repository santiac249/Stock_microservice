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
import org.springframework.data.domain.Pageable;
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
    public PageCustom<Product> getProducts(PageRequestCustom pageRequest, String brand, String category) {
        Sort sort = Sort.by(pageRequest.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC, pageRequest.getSortField());
        Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize(), sort);

        Page<ProductEntity> pageResult;

        //Validaciones
        if (brand != null && !brand.isEmpty() && category != null && !category.isEmpty()) {
            pageResult = productRepository.findByBrandNameContainingIgnoreCaseAndCategoriesNameContainingIgnoreCase(brand, category, pageable);
        } else if (brand != null && !brand.isEmpty()) {
            pageResult = productRepository.findByBrandNameContainingIgnoreCase(brand, pageable);
        } else if (category != null && !category.isEmpty()) {
            pageResult = productRepository.findByCategoriesNameContainingIgnoreCase(category, pageable);
        } else {
            pageResult = productRepository.findAll(pageable);
        }

        List<Product> products = productEntityMapper.toProductList(pageResult.getContent());

        return new PageCustom<>(
                products,
                (int) pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.getNumber(),
                pageRequest.isAscending()
        );
    }
}

