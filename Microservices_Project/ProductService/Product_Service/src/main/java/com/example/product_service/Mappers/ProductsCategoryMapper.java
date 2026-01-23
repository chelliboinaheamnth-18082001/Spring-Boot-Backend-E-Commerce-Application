package com.example.product_service.Mappers;

import com.example.product_service.Entites.ProductCategory;
import com.example.product_service.Product_DTOs.ProductCategoryDTO;
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
