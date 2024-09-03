package com.stock_microservice.stock_microservice.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private double price;

    // Relación con la entidad Marca (muchos productos pueden pertenecer a una marca)
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    // Relación con la entidad Category (muchos productos pueden pertenecer a muchas (en este caso 3) categorias)
    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryEntity> categories;

}
