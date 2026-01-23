package com.example.cart_order_service.Controllers.CartItemControllers;

import com.example.cart_order_service.DTOs.CartItemDTOs.CartItemRequestDTO;
import com.example.cart_order_service.DTOs.CartItemDTOs.CartItemsResponseDTO;
import com.example.cart_order_service.Services.CartItemServices.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItemsResponseDTO> createCartItem(@RequestBody CartItemRequestDTO
                                                                           cartItemRequestDTO,
                                                               @RequestHeader("X-User-ID") Long userId ) {
        CartItemsResponseDTO cartItem = cartItemService.createCartItem(userId, cartItemRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
    }

    @GetMapping
    public ResponseEntity<List<CartItemsResponseDTO>> getAllCartItems(@RequestHeader("X-User-ID") Long userId) {
       List<CartItemsResponseDTO> cartItemsResponseDTOList=cartItemService.getAllCartItems(userId);
       return ResponseEntity.ok(cartItemsResponseDTOList);
    }

}
