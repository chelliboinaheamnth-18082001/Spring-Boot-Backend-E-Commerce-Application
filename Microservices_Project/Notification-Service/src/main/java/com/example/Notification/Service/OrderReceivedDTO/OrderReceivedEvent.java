package com.example.Notification.Service.OrderReceivedDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReceivedEvent {

    private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;
    private String orderStatus;

    private List<OrderItemEvent> items;

    private LocalDateTime createdAt;
}