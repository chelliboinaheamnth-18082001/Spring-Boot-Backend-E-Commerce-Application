package com.example.monolithicbackend.DTO.UsersRelatedDTOs;

import com.example.monolithicbackend.Entities.UserRole;
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
