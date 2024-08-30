package com.stock_microservice.stock_microservice.application.handler;

import com.stock_microservice.stock_microservice.application.dto.BrandRequest;
import com.stock_microservice.stock_microservice.application.dto.BrandResponse;
import com.stock_microservice.stock_microservice.application.mapper.BrandRequestMapper;
import com.stock_microservice.stock_microservice.application.mapper.BrandResponseMapper;
import com.stock_microservice.stock_microservice.domain.Pagination.PageCustom;
import com.stock_microservice.stock_microservice.domain.Pagination.PageRequestCustom;
import com.stock_microservice.stock_microservice.domain.api.IBrandServicePort;
import com.stock_microservice.stock_microservice.domain.model.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler{

    private final IBrandServicePort brandServicePort;
    private final BrandRequestMapper brandRequestMapper;
    private final BrandResponseMapper brandResponseMapper;

    @Override
    public void saveBrand(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.toBrand(brandRequest);
        brandServicePort.saveBrand(brand);
    }

    @Override
    public BrandResponse getBrandById(Long id) {
        Brand brand = brandServicePort.getBrandById(id);
        return brandResponseMapper.toResponse(brand);
    }

    @Override
    public BrandResponse getBrandByName(String name) {
        Brand brand = brandServicePort.getBrandByName(name);
        return brandResponseMapper.toResponse(brand);
    }

    @Override
    public List<BrandResponse> getAllBrands() {
        return brandResponseMapper.toResponseList(brandServicePort.getAllBrands());
    }

    @Override
    public PageCustom<BrandResponse> getBrands(PageRequestCustom pageRequest) {
        PageCustom<Brand> brandsPage = brandServicePort.getBrands(pageRequest);
        List<BrandResponse> responseList = brandResponseMapper.toResponseList(brandsPage.getContent());
        return new PageCustom<>(
                responseList,
                brandsPage.getTotalElements(),
                brandsPage.getTotalPages(),
                brandsPage.getCurrentPage(),
                brandsPage.isAscending()
        );
    }
}
