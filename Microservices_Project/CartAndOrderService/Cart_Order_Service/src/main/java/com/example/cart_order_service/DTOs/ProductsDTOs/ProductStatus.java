package com.example.cart_order_service.DTOs.ProductsDTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ProductStatus {
    ACTIVE,
    OUT_OF_STOCK,
    DISABLED
}
