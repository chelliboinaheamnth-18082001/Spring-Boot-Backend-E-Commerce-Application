package com.example.cart_order_service.Controllers.OrderControllers;

import com.example.cart_order_service.DTOs.OrdersDto.OrderResponseDTO;
import com.example.cart_order_service.Services.OrderServices.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestHeader ("X-User-ID") Long userId)
    {
        OrderResponseDTO order = orderService.createOrder(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

}
