package com.example.monolithicbackend.Services.CartItemServices;

import com.example.monolithicbackend.DTO.CartItemDTOs.CartItemRequestDTO;
import com.example.monolithicbackend.DTO.CartItemDTOs.CartItemsResponseDTO;
import com.example.monolithicbackend.Entities.CartItem;
import com.example.monolithicbackend.Entities.Products;
import com.example.monolithicbackend.Entities.Users;
import com.example.monolithicbackend.ExceptionHandling.CartExceptionHandler.CartItemNotFoundException;
import com.example.monolithicbackend.ExceptionHandling.ProductsRelatedExceptionHandler.ProductNotFoundException;
import com.example.monolithicbackend.ExceptionHandling.User_Related_ExceptionHandling.UserNotFoundException;
import com.example.monolithicbackend.Mappers.CartItemsMappers.CartItemMapper;
import com.example.monolithicbackend.Repositories.CartItemRepo;
import com.example.monolithicbackend.Repositories.ProductsRepo;
import com.example.monolithicbackend.Repositories.UserRepo;
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
    private final UserRepo usersRepo;
    private final ProductsRepo productsRepo;
    private final CartItemMapper cartItemMapper;

    public CartItemsResponseDTO createCartItem(Long userId, CartItemRequestDTO cartItemRequestDTO) {

        Optional<Users> byId = usersRepo.findById(userId);
        if(byId.isEmpty())
        {
            throw new UserNotFoundException("User Not Found");
        }
        Users users = byId.get();

        Optional<Products> byId1 = productsRepo.findById(cartItemRequestDTO.getProductId());
        if(byId1.isEmpty())
        {
            throw new ProductNotFoundException("Product Not Found");
        }
        Products products = byId1.get();

        Optional<CartItem> byUserAndProduct = cartItemRepo.findByUserAndProduct(users, products);

        CartItem savedItems=null;
        if(byUserAndProduct.isPresent())
        {
            CartItem cartItem = byUserAndProduct.get();
            cartItem.setQuantity(cartItem.getQuantity()
                   + cartItemRequestDTO.getQuantity());

            cartItem.setPrice(productsRepo.findById(cartItemRequestDTO.getProductId())
                   .get().getPrice().multiply(new BigDecimal(cartItemRequestDTO.getQuantity())));
           savedItems=cartItemRepo.save(cartItem);
        }
        else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(users);
            cartItem.setProduct(products);
            cartItem.setQuantity(cartItemRequestDTO.getQuantity());
            cartItem.setPrice(productsRepo.findById(cartItemRequestDTO.getProductId())
                   .get().getPrice().multiply(new BigDecimal(cartItemRequestDTO.getQuantity())));
            savedItems=cartItemRepo.save(cartItem);
        }

        return cartItemMapper.
                MapCartItemToCartItemsResponseDTO(savedItems,userId,byId1.get().getId());

    }

    public List<CartItemsResponseDTO> getAllCartItems(Long userId) {

        Optional<Users> byId = usersRepo.findById(userId);
        if(byId.isEmpty())
        {
            throw new UserNotFoundException("User Not Found");
        }
        Users users = byId.get();
        List<CartItem> cartItems = cartItemRepo.findByUser(users);
        List<CartItemsResponseDTO> cartItemsResponseDTOList = cartItems.stream()
                .map(cartItem -> cartItemMapper.MapCartItemToCartItemsResponseDTO(cartItem,
                        userId, cartItem.getProduct().getId()))
                .toList();

        if(cartItemsResponseDTOList.isEmpty())
        {
            throw new CartItemNotFoundException("Cart Items Not Found");
        }
        return cartItemsResponseDTOList;
    }

    public void deleteCartItems(Long userId) {
        Optional<Users> byId = usersRepo.findById(userId);
        if(byId.isEmpty())
        {
            throw new UserNotFoundException("User Not Found");
        }
        Users users = byId.get();
        cartItemRepo.deleteByUser(users);
    }

    @Transactional
    public boolean deleteCartItemByUser(Users user)
    {
        boolean isDeleted=false;
        Optional<Users> byId = usersRepo.findById(user.getId());
        if(byId.isEmpty())
        {
            isDeleted=false;
            throw new UserNotFoundException("User Not Found");
        }
        Users users = byId.get();
        cartItemRepo.deleteByUser(users);
        isDeleted=true;
        return isDeleted;

    }
}
