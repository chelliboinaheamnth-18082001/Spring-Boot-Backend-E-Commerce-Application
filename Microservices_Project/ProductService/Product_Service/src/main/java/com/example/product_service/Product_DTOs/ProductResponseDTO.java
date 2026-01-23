package com.example.product_service.Product_DTOs;

import com.example.product_service.Entites.ProductStatus;
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
