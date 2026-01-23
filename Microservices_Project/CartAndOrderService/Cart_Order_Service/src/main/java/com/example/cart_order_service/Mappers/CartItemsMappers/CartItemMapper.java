package com.example.cart_order_service.Mappers.CartItemsMappers;


import com.example.cart_order_service.DTOs.CartItemDTOs.CartItemsResponseDTO;
import com.example.cart_order_service.Entites.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemMapper {

//    private final UserRepo usersRepo;
//    private final ProductsRepo productsRepo;
//    private final UserMappers userMappers;
//    private final ProductMapper productMappers;

    public CartItemsResponseDTO MapCartItemToCartItemsResponseDTO(CartItem cartItem, Long userId,
                                                                  String productId) {
        CartItemsResponseDTO dto = new CartItemsResponseDTO();




        dto.setUserId(String.valueOf(userId));
        dto.setProductid(productId);

        dto.setQuantity(cartItem.getQuantity());
        dto.setPrice(cartItem.getPrice());

        return dto;
    }
}