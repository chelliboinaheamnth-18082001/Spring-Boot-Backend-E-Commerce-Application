package com.example.monolithicbackend.Mappers.OrdersMapper;

import com.example.monolithicbackend.DTO.OrdersDto.OrderResponseDTO;
import com.example.monolithicbackend.Entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapperClass {

    private final OrderItemsMapper orderItemMapper;
    public OrderResponseDTO MapOrderToOrderResponseDTO(Order order)
    {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setUserId(order.getUserId());
        orderResponseDTO.setTotalAmount(order.getTotalAmount());
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setCreatedAt(order.getCreatedAt());
        orderResponseDTO.setItems(order.getItems().stream().
                map(orderItemMapper::MapOrderItemToOrderItemResponseDTO).toList());
        return orderResponseDTO;
    }
}
