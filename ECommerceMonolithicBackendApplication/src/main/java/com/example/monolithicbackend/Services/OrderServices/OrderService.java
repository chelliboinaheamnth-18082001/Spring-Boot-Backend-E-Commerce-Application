package com.example.monolithicbackend.Services.OrderServices;

import com.example.monolithicbackend.DTO.OrdersDto.OrderResponseDTO;
import com.example.monolithicbackend.Entities.*;
import com.example.monolithicbackend.ExceptionHandling.OrdersExceptionHandling.OrderUserNotFoundException;
import com.example.monolithicbackend.Mappers.OrdersMapper.OrderMapperClass;
import com.example.monolithicbackend.Repositories.CartItemRepo;
import com.example.monolithicbackend.Repositories.OrderRepo;
import com.example.monolithicbackend.Repositories.UserRepo;
import com.example.monolithicbackend.Services.CartItemServices.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepo userRepo;
    private final CartItemRepo cartItemRepo;
    private final OrderRepo orderRepo;
    private final CartItemService cartItemService;
    private final OrderMapperClass orderMapperClass;

    public OrderResponseDTO createOrder(Long userId) {

        Optional<Users> byId = userRepo.findById(userId);
        if (!byId.isPresent()) {
            throw new OrderUserNotFoundException("Invalid User Or User Not Registered");
        }
        Users users = byId.get();

        List<CartItem> cartItemList = cartItemRepo.findByUser(users);

        if(cartItemList.isEmpty()) {
            throw new OrderUserNotFoundException("Cart Is Empty");
        }

        BigDecimal totalAmount = cartItemList.stream()
                .map(cartItem -> cartItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        List<OrderItem> orderItemList = cartItemList.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProduct().getId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setOrder(order);
            return orderItem;
        }).toList();
        order.setItems(orderItemList);
        order.setStatus(OrderStatus.CONFIRMED);
        Order savedOrder = orderRepo.save(order);

        boolean b = cartItemService.deleteCartItemByUser(users);
        if(!b)
        {
            throw new OrderUserNotFoundException("Cart Items Not Deleted");
        }
        return orderMapperClass.MapOrderToOrderResponseDTO(savedOrder);


    }

}
