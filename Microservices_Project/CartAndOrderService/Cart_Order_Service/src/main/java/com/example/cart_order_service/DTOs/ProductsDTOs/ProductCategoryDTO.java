package com.example.cart_order_service.DTOs.ProductsDTOs;

import lombok.Data;

@Data
public class ProductCategoryDTO {
    private String name;
    private String brandName;   // ✅ camelCase
    private String modelName;   // ✅ camelCase
}