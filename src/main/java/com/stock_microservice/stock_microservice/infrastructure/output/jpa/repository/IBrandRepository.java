package com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository;

import com.stock_microservice.stock_microservice.infrastructure.output.jpa.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findByName(String name);

    Optional<BrandEntity> findById(Long id);
}
