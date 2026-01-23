package com.example.cart_order_service.Services.OrderServices;


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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final CartItemRepo cartItemRepo;
    private final OrderRepo orderRepo;
    private final CartItemService cartItemService;
    private final OrderMapperClass orderMapperClass;

    public OrderResponseDTO createOrder(Long userId) {

//        Optional<Users> byId = userRepo.findById(userId);
//        if (!byId.isPresent()) {
//            throw new OrderUserNotFoundException("Invalid User Or User Not Registered");
//        }
//        Users users = byId.get();

        List<CartItem> cartItemList = cartItemRepo.findByUserId(String.valueOf(userId));

        if(cartItemList.isEmpty()) {
            throw new OrderUserNotFoundException("Cart Is Empty");
        }

        BigDecimal totalAmount = cartItemList.stream()
                .map(cartItem -> cartItem.getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        List<OrderItem> orderItemList = cartItemList.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(String.valueOf(cartItem.getProductId()));
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setOrder(order);
            return orderItem;
        }).toList();
        order.setItems(orderItemList);
        order.setStatus(OrderStatus.CONFIRMED);
        Order savedOrder = orderRepo.save(order);

        boolean b = cartItemService.deleteCartItemByUser(String.valueOf(userId));
        if(!b)
        {
            throw new OrderUserNotFoundException("Cart Items Not Deleted");
        }
        return orderMapperClass.MapOrderToOrderResponseDTO(savedOrder);


    }

}
