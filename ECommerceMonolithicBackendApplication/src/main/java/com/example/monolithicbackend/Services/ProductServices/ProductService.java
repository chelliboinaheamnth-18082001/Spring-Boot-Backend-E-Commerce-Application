package com.example.monolithicbackend.Services.ProductServices;


import com.example.monolithicbackend.DTO.Product_Related_DTOs.ProductRequestDTO;
import com.example.monolithicbackend.DTO.Product_Related_DTOs.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    public List<ProductResponseDTO> getAllProducts();

    public ProductResponseDTO getProductById(Long id);

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);

    public void deleteProduct(Long id);

    public List<ProductResponseDTO> searchProductByKeyword(String Keyword);
}
