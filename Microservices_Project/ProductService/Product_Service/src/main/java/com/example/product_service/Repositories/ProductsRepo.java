package com.example.product_service.Repositories;

import com.example.product_service.Entites.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepo extends JpaRepository<Products,Long> {


    boolean existsByModelName(String modelName);


        @Query("""
           SELECT p FROM Products p
           WHERE LOWER(p.modelName) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(p.brandName) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
           """)
        List<Products> searchByKeyword(@Param("keyword") String keyword);

}
