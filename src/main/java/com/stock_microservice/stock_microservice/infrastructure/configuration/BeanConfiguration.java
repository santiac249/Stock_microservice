package com.stock_microservice.stock_microservice.infrastructure.configuration;

import com.stock_microservice.stock_microservice.domain.api.IBrandServicePort;
import com.stock_microservice.stock_microservice.domain.api.ICategoryServicePort;
import com.stock_microservice.stock_microservice.domain.api.IProductServicePort;
import com.stock_microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import com.stock_microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import com.stock_microservice.stock_microservice.domain.spi.IProductPersistencePort;
import com.stock_microservice.stock_microservice.domain.usecase.BrandUsecase;
import com.stock_microservice.stock_microservice.domain.usecase.CategoryUsecase;
import com.stock_microservice.stock_microservice.domain.usecase.ProductUsecase;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.adapter.BrandJpaAdapter;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.adapter.CategoryJpaAdapter;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.adapter.ProductJpaAdapter;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper.BrandEntityMapper;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper.ProductEntityMapper;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository.IBrandRepository;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository.ICategoryRepository;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;
    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUsecase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public IBrandServicePort brandServicePort(){
        return new BrandUsecase(brandPersistencePort());
    }

    @Bean
    public IProductPersistencePort productPersistencePort() {
        return new ProductJpaAdapter(productRepository, productEntityMapper);
    }

    @Bean
    public IProductServicePort productServicePort(){
        return new ProductUsecase(productPersistencePort(), categoryPersistencePort(), brandPersistencePort());
    }

}
