package com.stock_microservice.stock_microservice.application.handler;

import com.stock_microservice.stock_microservice.application.dto.BrandRequest;
import com.stock_microservice.stock_microservice.application.mapper.BrandRequestMapper;
import com.stock_microservice.stock_microservice.application.mapper.BrandResponseMapper;
import com.stock_microservice.stock_microservice.domain.api.IBrandServicePort;
import com.stock_microservice.stock_microservice.domain.model.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
}
