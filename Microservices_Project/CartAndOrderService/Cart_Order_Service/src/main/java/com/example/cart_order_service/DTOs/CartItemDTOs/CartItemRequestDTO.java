package com.example.cart_order_service.DTOs.CartItemDTOs;

import lombok.Data;

@Data
public class CartItemRequestDTO {

    String productId;

    Integer quantity;
}
