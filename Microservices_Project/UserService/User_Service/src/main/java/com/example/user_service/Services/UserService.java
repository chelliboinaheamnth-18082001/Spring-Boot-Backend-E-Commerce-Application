package com.example.user_service.Services;


import com.example.user_service.User_DTOs.UserRequestDto;
import com.example.user_service.User_DTOs.UserResponseDTO;

import java.util.List;

public interface UserService {

    public UserResponseDTO createUser(UserRequestDto userRequestDto);
    public UserResponseDTO getUserById(Long id);
    public UserResponseDTO updateUser(Long id, UserRequestDto userRequestDto);
    public void deleteUser(Long id);
    public List<UserResponseDTO> getAllUsers();
}
