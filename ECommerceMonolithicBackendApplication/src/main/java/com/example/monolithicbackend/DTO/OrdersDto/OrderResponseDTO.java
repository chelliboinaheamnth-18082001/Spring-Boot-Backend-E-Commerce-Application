package com.example.monolithicbackend.DTO.OrdersDto;

import com.example.monolithicbackend.Entities.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemResponseDTO> items;
    private LocalDateTime createdAt;
}
