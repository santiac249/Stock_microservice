package com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository;

import com.stock_microservice.stock_microservice.infrastructure.output.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByName(String name);

    Optional<ProductEntity> findById(Long id);
}
