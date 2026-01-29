package com.example.cart_order_service.DTOs.ProductsDTOs;


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
