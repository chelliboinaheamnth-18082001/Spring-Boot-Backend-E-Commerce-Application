package com.example.monolithicbackend.DTO.CartItemDTOs;

import lombok.Data;

@Data
public class CartItemRequestDTO {

    Long productId;

    Integer quantity;
}
