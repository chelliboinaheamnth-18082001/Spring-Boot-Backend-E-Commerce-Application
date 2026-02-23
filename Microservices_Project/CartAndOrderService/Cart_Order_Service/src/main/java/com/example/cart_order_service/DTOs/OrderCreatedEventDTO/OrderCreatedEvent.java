package com.example.cart_order_service.DTOs.OrderCreatedEventDTO;

import com.example.cart_order_service.Entites.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;
    private Long userId;

    private BigDecimal totalAmount;
    private OrderStatus status;

    private List<OrderItemEvent> items;

    private LocalDateTime createdAt;

    // ===============================
    // INNER DTO FOR ORDER ITEMS
    // ===============================
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemEvent {
        private String productId;
        private Integer quantity;
        private BigDecimal price;
    }
}