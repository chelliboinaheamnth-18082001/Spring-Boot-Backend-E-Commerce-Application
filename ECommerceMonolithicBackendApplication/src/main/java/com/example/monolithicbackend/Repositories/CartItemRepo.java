package com.example.monolithicbackend.Repositories;

import com.example.monolithicbackend.Entities.CartItem;
import com.example.monolithicbackend.Entities.Products;
import com.example.monolithicbackend.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByUserAndProduct(Users users, Products products);

    List<CartItem> findByUser(Users user);   // <-- new method

    void deleteByUser(Users users);
}
