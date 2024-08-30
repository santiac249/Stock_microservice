package com.stock_microservice.stock_microservice.infrastructure.output.jpa.adapter;

import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;
import com.stock_microservice.stock_microservice.domain.exception.DuplicateBrandNameException;
import com.stock_microservice.stock_microservice.domain.model.Brand;
import com.stock_microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import com.stock_microservice.stock_microservice.infrastructure.exception.BrandNotFoundException;
import com.stock_microservice.stock_microservice.infrastructure.exception.NoDataFoundException;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.entity.BrandEntity;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.mapper.BrandEntityMapper;
import com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

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

    @Override
    public List<Brand> getAllBrands() {
        List<BrandEntity> brandEntityList = brandRepository.findAll();
        if(brandEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return brandEntityMapper.toBrandList(brandEntityList);
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandEntityMapper.toBrand(brandRepository.findById(id)
                .orElseThrow(BrandNotFoundException::new));
    }

    @Override
    public Brand getBrandByName(String name) {
        return brandEntityMapper.toBrand(brandRepository.findByName(name)
                .orElseThrow(BrandNotFoundException::new));
    }

    @Override
    public PageCustom<Brand> getBrands(PageRequestCustom pageRequest) {
        PageRequest pageRequestSpring = PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getSize(),
                pageRequest.isAscending() ? Sort.by(pageRequest.getSortField()).ascending() : Sort.by(pageRequest.getSortField()).descending()
        );

        Page<BrandEntity> brandEntityPage = brandRepository.findAll(pageRequestSpring);

        List<Brand> brands = brandEntityMapper.toBrandList(brandEntityPage.getContent());

        return new PageCustom<>(
                brands,
                (int) brandEntityPage.getTotalElements(),
                brandEntityPage.getTotalPages(),
                brandEntityPage.getNumber(),
                pageRequest.isAscending()
        );
    }
}
