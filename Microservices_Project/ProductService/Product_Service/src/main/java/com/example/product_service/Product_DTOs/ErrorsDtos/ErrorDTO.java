package com.example.product_service.Product_DTOs.ErrorsDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    private String timestamp;   // When the error happened
    private String message;     // Human-readable message
    private String path;        // API endpoint URL
    private String method;      // HTTP method (GET/POST)
    private int status;         // HTTP status code
}
