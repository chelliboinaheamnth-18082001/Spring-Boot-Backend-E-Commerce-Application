package com.example.cart_order_service.Mappers.OrdersMapper;


import com.example.cart_order_service.DTOs.OrdersDto.OrderItemResponseDTO;
import com.example.cart_order_service.Entites.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemsMapper {
    public OrderItemResponseDTO MapOrderItemToOrderItemResponseDTO(OrderItem orderItem)
    {
        OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
        orderItemResponseDTO.setProductId(String.valueOf(orderItem.getProductId()));
        orderItemResponseDTO.setQuantity(orderItem.getQuantity());
        orderItemResponseDTO.setPrice(orderItem.getPrice());
        return orderItemResponseDTO;
    }
}
