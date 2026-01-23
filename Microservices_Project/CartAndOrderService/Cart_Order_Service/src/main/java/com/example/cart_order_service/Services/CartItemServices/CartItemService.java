package com.example.cart_order_service.Services.CartItemServices;


import com.example.cart_order_service.DTOs.CartItemDTOs.CartItemRequestDTO;
import com.example.cart_order_service.DTOs.CartItemDTOs.CartItemsResponseDTO;
import com.example.cart_order_service.Entites.CartItem;
import com.example.cart_order_service.ExceptionHandlers.CartExceptionHandler.CartItemNotFoundException;
import com.example.cart_order_service.Mappers.CartItemsMappers.CartItemMapper;
import com.example.cart_order_service.Repositories.CartItemRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepo cartItemRepo;
//    private final UserRepo usersRepo;
//    private final ProductsRepo productsRepo;
    private final CartItemMapper cartItemMapper;

    public CartItemsResponseDTO createCartItem(Long userId, CartItemRequestDTO cartItemRequestDTO) {

//        Optional<Users> byId = usersRepo.findById(userId);
//        if(byId.isEmpty())
//        {
//            throw new UserNotFoundException("User Not Found");
//        }
//        Users users = byId.get();
//
//        Optional<Products> byId1 = productsRepo.findById(cartItemRequestDTO.getProductId());
//        if(byId1.isEmpty())
//        {
//            throw new ProductNotFoundException("Product Not Found");
//        }
//        Products products = byId1.get();

        Optional<CartItem> byUserAndProduct = cartItemRepo.findByUserIdAndProductId(String.valueOf(userId),
                cartItemRequestDTO.getProductId());

        CartItem savedItems=null;
        if(byUserAndProduct.isPresent())
        {
            CartItem cartItem = byUserAndProduct.get();
            cartItem.setQuantity(cartItem.getQuantity()
                   + cartItemRequestDTO.getQuantity());

            cartItem.setPrice(BigDecimal.valueOf(1000));
           savedItems=cartItemRepo.save(cartItem);
        }
        else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(String.valueOf(userId));
            cartItem.setProductId(cartItemRequestDTO.getProductId())   ;
            cartItem.setQuantity(cartItemRequestDTO.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000));
            savedItems=cartItemRepo.save(cartItem);
        }

        return cartItemMapper.
                MapCartItemToCartItemsResponseDTO(savedItems,userId,
                        cartItemRequestDTO.getProductId());

    }

    public List<CartItemsResponseDTO> getAllCartItems(Long userId) {


        List<CartItem> cartItems = cartItemRepo.findByUserId(String.valueOf(userId));
        List<CartItemsResponseDTO> cartItemsResponseDTOList = cartItems.stream()
                .map(cartItem -> cartItemMapper.MapCartItemToCartItemsResponseDTO(cartItem,
                        userId, cartItem.getProductId()))
                .toList();

        if(cartItemsResponseDTOList.isEmpty())
        {
            throw new CartItemNotFoundException("Cart Items Not Found");
        }
        return cartItemsResponseDTOList;
    }

    public void deleteCartItems(Long userId) {


        cartItemRepo.deleteByUserId(String.valueOf(userId));
    }

    @Transactional
    public boolean deleteCartItemByUser(String userId)
    {
        boolean isDeleted=false;
        cartItemRepo.deleteByUserId(userId);
        isDeleted=true;
        return isDeleted;

    }
}
