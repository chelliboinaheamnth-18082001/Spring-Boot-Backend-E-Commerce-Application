package com.example.product_service.Repositories;


import com.example.product_service.Entites.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> {



    Optional<ProductCategory> findByName(String category);
}
