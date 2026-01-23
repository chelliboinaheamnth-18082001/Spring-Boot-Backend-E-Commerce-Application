package com.example.product_service.Product_DTOs;

import com.example.product_service.Entites.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDTO {
    private String brandName;

    private String modelName;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private String category;


    private String imageUrl;

    private ProductStatus status = ProductStatus.ACTIVE;
}
