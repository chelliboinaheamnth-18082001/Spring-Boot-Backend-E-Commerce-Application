package com.example.cart_order_service.DTOs.OrdersDto;


import com.example.cart_order_service.Entites.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private String userId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemResponseDTO> items;
    private LocalDateTime createdAt;
}
