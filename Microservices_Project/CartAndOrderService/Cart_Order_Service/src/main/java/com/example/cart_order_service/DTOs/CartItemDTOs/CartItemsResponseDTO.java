package com.example.cart_order_service.DTOs.CartItemDTOs;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemsResponseDTO {
    private String userId;
    private String productid;
    private Integer quantity;
    private BigDecimal price;
}
