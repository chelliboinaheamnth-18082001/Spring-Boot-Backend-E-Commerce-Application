package com.example.cart_order_service.Services.OrderServices;

import com.example.cart_order_service.DTOs.OrderCreatedEventDTO.OrderCreatedEvent;
import com.example.cart_order_service.DTOs.OrdersDto.OrderResponseDTO;
import com.example.cart_order_service.Entites.CartItem;
import com.example.cart_order_service.Entites.Order;
import com.example.cart_order_service.Entites.OrderItem;
import com.example.cart_order_service.Entites.OrderStatus;
import com.example.cart_order_service.ExceptionHandlers.OrdersExceptionHandling.OrderUserNotFoundException;
import com.example.cart_order_service.Mappers.OrdersMapper.OrderMapperClass;
import com.example.cart_order_service.Repositories.CartItemRepo;
import com.example.cart_order_service.Repositories.OrderRepo;
import com.example.cart_order_service.Services.CartItemServices.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartItemRepo cartItemRepo;
    private final OrderRepo orderRepo;
    private final CartItemService cartItemService;
    private final OrderMapperClass orderMapperClass;
    private final StreamBridge streamBridge;

    public OrderResponseDTO createOrder(Long userId) {

        // 1️⃣ Fetch cart items
        List<CartItem> cartItemList =
                cartItemRepo.findByUserId(String.valueOf(userId));

        if (cartItemList.isEmpty()) {
            throw new OrderUserNotFoundException("Cart Is Empty");
        }

        // 2️⃣ Calculate total amount
        BigDecimal totalAmount = cartItemList.stream()
                .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3️⃣ Create Order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = cartItemList.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(String.valueOf(cartItem.getProductId()));
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setOrder(order);
            return orderItem;
        }).toList();

        order.setItems(orderItems);
        order.setStatus(OrderStatus.CONFIRMED);

        Order savedOrder = orderRepo.save(order);

        // 4️⃣ Build Event
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .totalAmount(savedOrder.getTotalAmount())
                .status(savedOrder.getStatus())
                .createdAt(savedOrder.getCreatedAt())
                .items(
                        savedOrder.getItems().stream()
                                .map(item ->
                                        OrderCreatedEvent.OrderItemEvent.builder()
                                                .productId(item.getProductId())
                                                .quantity(item.getQuantity())
                                                .price(item.getPrice())
                                                .build()
                                ).toList()
                )
                .build();

        // 5️⃣ Publish Event using Spring Cloud Stream
        streamBridge.send("createOrder-out-0", event);

        // 6️⃣ Clear cart
        boolean deleted =
                cartItemService.deleteCartItemByUser(
                        String.valueOf(userId)
                );

        if (!deleted) {
            throw new OrderUserNotFoundException(
                    "Cart Items Not Deleted"
            );
        }

        // 7️⃣ Return response
        return orderMapperClass
                .MapOrderToOrderResponseDTO(savedOrder);
    }
}