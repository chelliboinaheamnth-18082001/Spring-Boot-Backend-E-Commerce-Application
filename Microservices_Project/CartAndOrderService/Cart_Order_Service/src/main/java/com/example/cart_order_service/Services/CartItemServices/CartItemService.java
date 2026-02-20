package com.example.cart_order_service.Services.CartItemServices;

import com.example.cart_order_service.DTOs.CartItemDTOs.CartItemRequestDTO;
import com.example.cart_order_service.DTOs.CartItemDTOs.CartItemsResponseDTO;
import com.example.cart_order_service.DTOs.ProductsDTOs.ProductResponseDTO;
import com.example.cart_order_service.DTOs.UserDTOs.UserResponseDTO;
import com.example.cart_order_service.Entites.CartItem;
import com.example.cart_order_service.ExceptionHandlers.CartExceptionHandler.CartItemNotFoundException;
import com.example.cart_order_service.ExceptionHandlers.ProductRelatedExceptions.ProductNotFoundException;
import com.example.cart_order_service.ExceptionHandlers.ProductRelatedExceptions.ProductOutOfStockException;
import com.example.cart_order_service.ExceptionHandlers.UserrelatedException.UserNotFoundException;
import com.example.cart_order_service.Inservice_Commnication_Client.ProductServiceClientInterface;
import com.example.cart_order_service.Inservice_Commnication_Client.UserServiceClientInterface;
import com.example.cart_order_service.Mappers.CartItemsMappers.CartItemMapper;
import com.example.cart_order_service.Repositories.CartItemRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepo cartItemRepo;
    private final ProductServiceClientInterface productServiceClient;
    private final CartItemMapper cartItemMapper;
    private final UserServiceClientInterface userServiceClientInterface;
    public static int counter=0;

    // ===============================
    // CREATE / ADD CART ITEM

    // ===============================
    @Transactional

    @CircuitBreaker(name = "ProductService", fallbackMethod = "createCartItemFallback")
    @Retry(name="retryBreaker",fallbackMethod = "createCartItemFallback")
    public CartItemsResponseDTO createCartItem(
            Long userId,
            CartItemRequestDTO requestDTO) {


        //Validating User Id
        UserResponseDTO userById;
        try
        {
            userById = userServiceClientInterface.getUserById(userId);
        }
        catch (ResponseStatusException ex) {
            throw new UserNotFoundException(
                    "User Not Found with ID: " + userId
            );
        }


        // 1️⃣ Validate quantity
        if (requestDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }


        // 2️⃣ Fetch product from Product Service
        ProductResponseDTO product;
        try {
            counter++;
            System.out.println("The Product Service Retried for : "+counter);
            product = productServiceClient.getProductById(
                    Long.valueOf(requestDTO.getProductId())
            );
        } catch (ResponseStatusException ex) {
            throw new ProductNotFoundException(
                    "Product Not Found with ID: " + requestDTO.getProductId()
            );
        }

        // 3️⃣ Check if cart item already exists
        Optional<CartItem> existingCartItem =
                cartItemRepo.findByUserIdAndProductId(
                        String.valueOf(userId),
                        requestDTO.getProductId()
                );

        int totalRequestedQuantity = requestDTO.getQuantity();

        if (existingCartItem.isPresent()) {
            totalRequestedQuantity += existingCartItem.get().getQuantity();
        }

        // 4️⃣ Validate stock availability
        if (product.getQuantity() < totalRequestedQuantity) {
            throw new ProductOutOfStockException(
                    "Available stock is " + product.getQuantity() +
                            ", total requested quantity is " + totalRequestedQuantity
            );
        }

        // 5️⃣ Save / Update cart item
        CartItem cartItem = existingCartItem.orElseGet(CartItem::new);

        cartItem.setUserId(String.valueOf(userId));
        cartItem.setProductId(requestDTO.getProductId());
        cartItem.setQuantity(totalRequestedQuantity);
        cartItem.setPrice(product.getPrice()); // ✅ REAL PRODUCT PRICE

        CartItem savedCartItem = cartItemRepo.save(cartItem);

        // 6️⃣ Map to response DTO
        return cartItemMapper.MapCartItemToCartItemsResponseDTO(
                savedCartItem,
                userId,
                requestDTO.getProductId()
        );
    }


    public CartItemsResponseDTO createCartItemFallback(
            Long userId,
            CartItemRequestDTO requestDTO,
            Throwable throwable) {

        // You can decide what to return when ProductService is down.
        // For example, return a placeholder response or throw a custom exception.
        throw new ProductNotFoundException(
                "Product Service is currently unavailable. Please try again later."
        );
    }

    // ===============================
    // GET ALL CART ITEMS
    // ===============================
    public List<CartItemsResponseDTO> getAllCartItems(Long userId) {

        List<CartItem> cartItems =
                cartItemRepo.findByUserId(String.valueOf(userId));

        if (cartItems.isEmpty()) {
            throw new CartItemNotFoundException("Cart Items Not Found");
        }

        return cartItems.stream()
                .map(item ->
                        cartItemMapper.MapCartItemToCartItemsResponseDTO(
                                item,
                                userId,
                                item.getProductId()
                        ))
                .toList();
    }

    // ===============================
    // DELETE ALL CART ITEMS
    // ===============================
    @Transactional
    public void deleteCartItems(Long userId) {
        cartItemRepo.deleteByUserId(String.valueOf(userId));
    }

    // ===============================
    // DELETE CART BY USER (UTILITY)
    // ===============================
    @Transactional
    public boolean deleteCartItemByUser(String userId) {
        cartItemRepo.deleteByUserId(userId);
        return true;
    }
}
