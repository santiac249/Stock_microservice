package com.stock_microservice.stock_microservice.infrastructure.configuration;

import com.stock_microservice.stock_microservice.domain.api.ICategoryServicePort;
import com.stock_microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import com.stock_microservice.stock_microservice.domain.usecase.CategoryUsecase;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.adapter.CategoryJpaAdapter;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUsecase(categoryPersistencePort());
    }

}
