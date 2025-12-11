package com.example.monolithicbackend.Repositories;
import com.example.monolithicbackend.Entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepo extends JpaRepository<Products,Long> {


    boolean existsByModelName(String modelName);
}
