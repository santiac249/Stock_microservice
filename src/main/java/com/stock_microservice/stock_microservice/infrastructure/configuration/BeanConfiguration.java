package com.stock_microservice.stock_microservice.infrastructure.configuration;

import com.stock_microservice.stock_microservice.domain.api.IBrandServicePort;
import com.stock_microservice.stock_microservice.domain.api.ICategoryServicePort;
import com.stock_microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import com.stock_microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import com.stock_microservice.stock_microservice.domain.usecase.BrandUsecase;
import com.stock_microservice.stock_microservice.domain.usecase.CategoryUsecase;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.adapter.BrandJpaAdapter;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.adapter.CategoryJpaAdapter;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper.BrandEntityMapper;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository.IBrandRepository;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository.ICategoryRepository;
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

}
