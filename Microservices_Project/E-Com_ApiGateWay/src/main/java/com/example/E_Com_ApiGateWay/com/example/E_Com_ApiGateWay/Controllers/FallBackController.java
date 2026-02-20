package com.example.E_Com_ApiGateWay.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @GetMapping("/product-service-fallback")
    public ResponseEntity<String> ProductServiceFallBack() {
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Product Service is down. Please try again later.");
    }
}
