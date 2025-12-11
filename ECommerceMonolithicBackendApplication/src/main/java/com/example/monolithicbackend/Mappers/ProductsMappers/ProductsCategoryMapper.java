package com.example.monolithicbackend.Mappers.ProductsMappers;

import com.example.monolithicbackend.DTO.Product_Related_DTOs.ProductCategoryDTO;
import com.example.monolithicbackend.Entities.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductsCategoryMapper {

    public ProductCategoryDTO MapProductCategoryToProductCategoryDTO(ProductCategory productCategory) {

        ProductCategoryDTO productCategory1 = new ProductCategoryDTO();
        //productCategory1.setDescription(productCategory.getDescription());
        productCategory1.setBrandName(productCategory.getBrandName());
        productCategory1.setModelName(productCategory.getModelName());
        productCategory1.setName(productCategory.getName());
        return productCategory1;

    }

}
