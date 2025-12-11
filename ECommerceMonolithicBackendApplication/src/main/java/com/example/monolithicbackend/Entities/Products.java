package com.example.monolithicbackend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Essential details
    @Column(nullable = false, length = 150)
    private String modelName;

    @Column(nullable = false, length = 150)
    private String brandName;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    // Category mapping (Many-to-One)
    // - LAZY = loads category only when used
    // - No referenceColumn needed (Hibernate automatically maps to "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id",nullable = false)
    private ProductCategory category;

    // Image URL (stored in cloud or local storage)
    @Column(length = 500)
    private String imageUrl;

    // Status of product
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status = ProductStatus.ACTIVE;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Automatically set timestamps
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
