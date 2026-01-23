package com.example.cart_order_service.Repositories;


import com.example.cart_order_service.Entites.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByUserIdAndProductId(String userId, String productId);

    List<CartItem> findByUserId(String userId);   // <-- new method

    void deleteByUserId(String userId);
}
