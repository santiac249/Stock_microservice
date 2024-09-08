package com.stock_microservice.stock_microservice.infrastructure.output.jpa.repository;

import com.stock_microservice.stock_microservice.infrastructure.output.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);

    Optional<CategoryEntity> findById(Long id);

    void deleteByName(String name);

    void deleteById(Long id);
}