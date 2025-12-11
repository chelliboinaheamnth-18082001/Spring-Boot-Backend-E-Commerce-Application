package com.example.monolithicbackend.Mappers.CartItemsMappers;

import com.example.monolithicbackend.DTO.CartItemDTOs.CartItemRequestDTO;
import com.example.monolithicbackend.DTO.CartItemDTOs.CartItemsResponseDTO;
import com.example.monolithicbackend.DTO.Product_Related_DTOs.ProductResponseDTO;
import com.example.monolithicbackend.DTO.UsersRelatedDTOs.UserResponseDTO;
import com.example.monolithicbackend.Entities.CartItem;
import com.example.monolithicbackend.Entities.Products;
import com.example.monolithicbackend.Entities.Users;
import com.example.monolithicbackend.Mappers.ProductsMappers.ProductMapper;
import com.example.monolithicbackend.Mappers.UserMappers;
import com.example.monolithicbackend.Repositories.ProductsRepo;
import com.example.monolithicbackend.Repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemMapper {

    private final UserRepo usersRepo;
    private final ProductsRepo productsRepo;
    private final UserMappers userMappers;
    private final ProductMapper productMappers;

    public CartItemsResponseDTO MapCartItemToCartItemsResponseDTO(CartItem cartItem,Long userId,
                                                                  long productId) {
        CartItemsResponseDTO dto = new CartItemsResponseDTO();
        Optional<Users> byId = usersRepo.findById(userId);

        Users users = byId.get();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO=userMappers.MpaUserToUserResponseDTO(users);
        dto.setUserResponseDTO(userResponseDTO);

        Optional<Products> byId1 = productsRepo.findById(productId);
        Products products = byId1.get();

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO=productMappers.MapProductToProductResponseDTO(products);
        dto.setProductResponseDTO(productResponseDTO);
        dto.setQuantity(cartItem.getQuantity());
        dto.setPrice(cartItem.getPrice());

        return dto;
    }
}