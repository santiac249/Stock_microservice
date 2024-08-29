package com.stock_microservice.stock_microservice.infrastructure.output.jpa.adapter;

import com.stock_microservice.stock_microservice.domain.exception.DuplicateBrandNameException;
import com.stock_microservice.stock_microservice.domain.model.Brand;
import com.stock_microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.entity.BrandEntity;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper.BrandEntityMapper;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    @Override
    public void saveBrand(Brand brand) {
        if(brandRepository.findByName(brand.getName()).isPresent()){
            throw new DuplicateBrandNameException(brand.getName());
        }
        BrandEntity brandEntity = brandEntityMapper.toEntity(brand);
        brandRepository.save(brandEntity);
    }
}
