package com.example.product_service.Entites;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ProductStatus {
    ACTIVE,
    OUT_OF_STOCK,
    DISABLED
}
