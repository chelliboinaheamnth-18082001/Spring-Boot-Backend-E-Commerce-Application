package com.example.product_service.Services.ServiceI_Implementation;


import com.example.product_service.Entites.ProductCategory;
import com.example.product_service.Entites.Products;
import com.example.product_service.ExceptionHandlers.ProductAlreadyExistsException;
import com.example.product_service.ExceptionHandlers.ProductNotFoundException;
import com.example.product_service.Mappers.ProductMapper;
import com.example.product_service.Product_DTOs.ProductRequestDTO;
import com.example.product_service.Product_DTOs.ProductResponseDTO;
import com.example.product_service.Repositories.ProductCategoryRepo;
import com.example.product_service.Repositories.ProductsRepo;
import com.example.product_service.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductsServiceImpl implements ProductService {

    private final ProductsRepo productsRepo;
    private final ProductCategoryRepo productCategoryRepo;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {

        // Normalize model name
        String normalizedModelName = productRequestDTO.getModelName()
                .toUpperCase()
                .trim()
                .replaceAll("\\s+", " ");

        // Check if product already exists
        boolean isProductExists = productsRepo.existsByModelName(normalizedModelName);
        if (isProductExists) {
            throw new ProductAlreadyExistsException("Product with name " +
                    normalizedModelName + " already exists");
        }

        // Map DTO to entity
        Products products = productMapper.MapProductDtoToProduct(productRequestDTO);

        // Ensure normalized model name is set before saving
        products.setModelName(normalizedModelName);

        // Save product
        productsRepo.save(products);

        // Map entity to response DTO
        return productMapper.MapProductToProductResponseDTO(products);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Products> all = productsRepo.findAll();
        if(all.isEmpty())
        {
            throw new RuntimeException("Products database is empty");
        }
        List<ProductResponseDTO> list = all.stream()
                .map(productMapper::MapProductToProductResponseDTO).toList();
        return list;
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {

        Optional<Products> byId = productsRepo.findById(id);
        if(byId.isEmpty())
        {
            throw new ProductNotFoundException("The Given Id Is Not Found In Products");
        }
        Products products = byId.get();
        ProductResponseDTO productResponseDTO = productMapper.MapProductToProductResponseDTO(products);
        return productResponseDTO;
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Optional<Products> byId = productsRepo.findById(id);
        if(byId.isEmpty())
        {
            throw new ProductNotFoundException("The Given Id Is Not Found In Products");
        }
        Products products = byId.get();
        products.setModelName(productRequestDTO.getModelName());
        products.setBrandName(productRequestDTO.getBrandName());
        products.setPrice(productRequestDTO.getPrice());
        products.setQuantity(productRequestDTO.getQuantity());
        products.setDescription(productRequestDTO.getDescription());
        products.setImageUrl(productRequestDTO.getImageUrl());

        ProductCategory category1 = byId.get().getCategory();
        Long id1 = category1.getId();
        Optional<ProductCategory> byId1 = productCategoryRepo.findById(id1);
        ProductCategory productCategory1 = byId1.get();
        productCategory1.setName(productRequestDTO.getCategory());
        productCategory1.setModelName(productRequestDTO.getModelName());
        productCategory1.setBrandName(productRequestDTO.getBrandName());
        ProductCategory save = productCategoryRepo.save(productCategory1);

        products.setCategory(save);
        productsRepo.save(products);

              ProductResponseDTO productResponseDTO = productMapper.
                MapProductToProductResponseDTO(products);
        return productResponseDTO;
    }

    @Override
    public void deleteProduct(Long id) {

        Optional<Products> byId = productsRepo.findById(id);
        if(byId.isEmpty())
        {
            throw new ProductNotFoundException("The Given Id Is Not Found In Products");
        }
        productsRepo.delete(byId.get());

    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> searchProductByKeyword(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword must not be empty");
        }

        String normalizedKeyword = keyword.trim().replaceAll("\\s+", " ");;

        List<Products> products = productsRepo.searchByKeyword(normalizedKeyword);

        if (products.isEmpty()) {
            throw new ProductNotFoundException(
                    "No products found for keyword: " + keyword
            );
        }

        return products.stream()
                .map(productMapper::MapProductToProductResponseDTO)
                .toList();
    }

}
