package com.example.monolithicbackend.Services.ProductServices.ServiceI_Implementation;

import com.example.monolithicbackend.DTO.Product_Related_DTOs.ProductRequestDTO;
import com.example.monolithicbackend.DTO.Product_Related_DTOs.ProductResponseDTO;
import com.example.monolithicbackend.Entities.BrandNames;
import com.example.monolithicbackend.Entities.ProductCategory;
import com.example.monolithicbackend.Entities.Products;
import com.example.monolithicbackend.ExceptionHandling.ProductsRelatedExceptionHandler.ProductAlreadyExistsException;
import com.example.monolithicbackend.ExceptionHandling.ProductsRelatedExceptionHandler.ProductNotFoundException;
import com.example.monolithicbackend.Mappers.ProductsMappers.ProductMapper;
import com.example.monolithicbackend.Repositories.ProductCategoryRepo;
import com.example.monolithicbackend.Repositories.ProductsRepo;
import com.example.monolithicbackend.Services.ProductServices.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductService {

    private final ProductsRepo productsRepo;
    private final ProductCategoryRepo productCategoryRepo;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {

        String modelName = productRequestDTO.getModelName();
        modelName=modelName.toUpperCase();
        modelName=modelName.trim().replaceAll("\\s+", " ");

        boolean isProductExists = productsRepo.
                existsByModelName(modelName);

        if(isProductExists)
        {
            throw new ProductAlreadyExistsException("Product with name " +
                    productRequestDTO.getModelName() + " already exists");
        }

        Products products = productMapper.MapProductDtoToProduct(productRequestDTO);
        productsRepo.save(products);
        ProductResponseDTO productResponseDTO = productMapper.
                MapProductToProductResponseDTO(products);
        return productResponseDTO;

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
    public List<ProductResponseDTO> searchProductByKeyword(String Keyword) {
        List<Products> all = productsRepo.findAll();
        List<ProductResponseDTO> list = new ArrayList<>();
        for(Products products:all)
        {
            if(products.getModelName().toUpperCase().contains(Keyword.toUpperCase())
                    ||products.getModelName().toLowerCase().contains(Keyword.toLowerCase())
                    || products.getDescription().toLowerCase().contains(Keyword.toLowerCase())
                    ||products.getDescription().toUpperCase().contains(Keyword.toUpperCase()))
            {
                ProductResponseDTO productResponseDTO = productMapper.
                        MapProductToProductResponseDTO(products);
                list.add(productResponseDTO);
            }
        }
        if(list.isEmpty())
        {
            throw new ProductNotFoundException("The Given Product Is Not Found with given "+ Keyword);
        }
        return list;
    }
}
