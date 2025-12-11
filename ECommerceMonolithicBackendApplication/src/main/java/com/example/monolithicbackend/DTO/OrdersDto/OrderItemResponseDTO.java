package com.example.monolithicbackend.DTO.OrdersDto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderItemResponseDTO {

    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
