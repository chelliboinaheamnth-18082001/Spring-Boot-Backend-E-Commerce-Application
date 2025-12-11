package com.example.monolithicbackend.Services.UserServices;

import com.example.monolithicbackend.DTO.UsersRelatedDTOs.UserRequestDto;
import com.example.monolithicbackend.DTO.UsersRelatedDTOs.UserResponseDTO;

import java.util.List;

public interface UserService {

    public UserResponseDTO createUser(UserRequestDto userRequestDto);
    public UserResponseDTO getUserById(Long id);
    public UserResponseDTO updateUser(Long id, UserRequestDto userRequestDto);
    public void deleteUser(Long id);
    public List<UserResponseDTO> getAllUsers();
}
