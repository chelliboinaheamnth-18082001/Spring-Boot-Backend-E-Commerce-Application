package com.example.monolithicbackend.Mappers.OrdersMapper;

import com.example.monolithicbackend.DTO.OrdersDto.OrderItemResponseDTO;
import com.example.monolithicbackend.Entities.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemsMapper {
    public OrderItemResponseDTO MapOrderItemToOrderItemResponseDTO(OrderItem orderItem)
    {
        OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
        orderItemResponseDTO.setProductId(orderItem.getProductId());
        orderItemResponseDTO.setQuantity(orderItem.getQuantity());
        orderItemResponseDTO.setPrice(orderItem.getPrice());
        return orderItemResponseDTO;
    }
}
