package com.example.cart_order_service.DTOs.UserDTOs;


import lombok.Data;

@Data
public class UserResponseDTO {
    private String firstName;

    private String lastName;

    private String email;

    private String mobileNumber;

    private AddressDTO address;

    private UserRole userRole;
}
