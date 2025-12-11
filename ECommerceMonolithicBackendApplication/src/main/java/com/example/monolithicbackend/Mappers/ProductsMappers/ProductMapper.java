    package com.example.monolithicbackend.Mappers.ProductsMappers;

    import com.example.monolithicbackend.DTO.Product_Related_DTOs.ProductCategoryDTO;
    import com.example.monolithicbackend.DTO.Product_Related_DTOs.ProductRequestDTO;
    import com.example.monolithicbackend.DTO.Product_Related_DTOs.ProductResponseDTO;
    import com.example.monolithicbackend.Entities.BrandNames;
    import com.example.monolithicbackend.Entities.ProductCategory;
    import com.example.monolithicbackend.Entities.Products;
    import com.example.monolithicbackend.Repositories.ProductCategoryRepo;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Component;

    import java.util.Optional;

    @RequiredArgsConstructor
    @Component
    public class ProductMapper {

        private final ProductCategoryRepo productCategoryRepo;
        private final ProductsCategoryMapper productsCategoryMapper;


        public Products MapProductDtoToProduct(ProductRequestDTO productRequestDto) {

            Products products = new Products();
            String modelName = productRequestDto.getModelName();
            modelName=modelName.toUpperCase();
            modelName=modelName.trim().replaceAll("\\s+", " ");
            products.setModelName(modelName);
            products.setBrandName(productRequestDto.getBrandName());
            products.setPrice(productRequestDto.getPrice());
            products.setQuantity(productRequestDto.getQuantity());
            products.setDescription(productRequestDto.getDescription());
            products.setImageUrl(productRequestDto.getImageUrl());
            String category = productRequestDto.getCategory();
            ProductCategory productCategory=new ProductCategory();
            productCategory.setName(productRequestDto.getCategory());
            productCategory.setModelName(productRequestDto.getModelName());
            productCategory.setBrandName(productRequestDto.getBrandName());
            //productCategory.setDescription(productRequestDTO.getDescription());
            ProductCategory save = productCategoryRepo.save(productCategory);
            products.setCategory(save);
            return products;

        }

        public ProductResponseDTO MapProductToProductResponseDTO(Products products) {

            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setModelName(products.getModelName());
            productResponseDTO.setBrandName(products.getBrandName());
            productResponseDTO.setPrice(products.getPrice());
            productResponseDTO.setQuantity(products.getQuantity());
            productResponseDTO.setDescription(products.getDescription());
            productResponseDTO.setImageUrl(products.getImageUrl());
            ProductCategory category = products.getCategory();
            ProductCategoryDTO productCategoryDto = productsCategoryMapper.
                    MapProductCategoryToProductCategoryDTO(category);
            productResponseDTO.setCategory(productCategoryDto);
            return productResponseDTO;
        }
    }
