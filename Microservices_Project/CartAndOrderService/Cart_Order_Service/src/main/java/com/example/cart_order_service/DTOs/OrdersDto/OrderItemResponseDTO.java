package com.example.cart_order_service.DTOs.OrdersDto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderItemResponseDTO {

    private String productId;
    private Integer quantity;
    private BigDecimal price;
}
