package com.example.cart_order_service.Mappers.OrdersMapper;


import com.example.cart_order_service.DTOs.OrdersDto.OrderResponseDTO;
import com.example.cart_order_service.Entites.Order;
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
        orderResponseDTO.setUserId(String.valueOf(order.getUserId()));
        orderResponseDTO.setTotalAmount(order.getTotalAmount());
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setCreatedAt(order.getCreatedAt());
        orderResponseDTO.setItems(order.getItems().stream().
                map(orderItemMapper::MapOrderItemToOrderItemResponseDTO).toList());
        return orderResponseDTO;
    }
}
