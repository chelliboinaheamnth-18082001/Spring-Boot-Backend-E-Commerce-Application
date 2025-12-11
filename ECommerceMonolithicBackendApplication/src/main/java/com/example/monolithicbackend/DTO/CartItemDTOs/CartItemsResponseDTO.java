package com.example.monolithicbackend.DTO.CartItemDTOs;

import com.example.monolithicbackend.DTO.Product_Related_DTOs.ProductResponseDTO;
import com.example.monolithicbackend.DTO.UsersRelatedDTOs.UserResponseDTO;
import com.example.monolithicbackend.Entities.Products;
import com.example.monolithicbackend.Entities.Users;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemsResponseDTO {
    private UserResponseDTO userResponseDTO;
    private ProductResponseDTO productResponseDTO;
    private Integer quantity;
    private BigDecimal price;
}
