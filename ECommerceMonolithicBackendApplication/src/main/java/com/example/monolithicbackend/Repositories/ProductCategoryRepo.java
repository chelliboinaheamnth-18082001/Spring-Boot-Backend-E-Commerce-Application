package com.example.monolithicbackend.Repositories;

import com.example.monolithicbackend.Entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> {



    Optional<ProductCategory> findByName(String category);
}
