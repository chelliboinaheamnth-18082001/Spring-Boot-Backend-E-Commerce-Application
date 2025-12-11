package com.example.monolithicbackend.DTO.Product_Related_DTOs;

import com.example.monolithicbackend.Entities.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {

    private String modelName;
    private String brandName;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private ProductCategoryDTO category;


    private String imageUrl;

    private ProductStatus status = ProductStatus.ACTIVE;
}
