package com.example.user_service.Controllers;


import com.example.user_service.Services.UserService;
import com.example.user_service.User_DTOs.UserRequestDto;
import com.example.user_service.User_DTOs.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDto userRequestDto) {
        if(userRequestDto==null)
        {
            throw new RuntimeException("Required fields are null");
        }
        UserResponseDTO user = userService.createUser(userRequestDto);
        ResponseEntity<UserResponseDTO> userResponseDTOResponseEntity = ResponseEntity.
                status(HttpStatus.CREATED).body(user);

        return userResponseDTOResponseEntity;

    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO userById = userService.getUserById(id);
        ResponseEntity<UserResponseDTO> body = ResponseEntity.status(HttpStatus.OK).
                body(userById);
        return body;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        UserResponseDTO userResponseDTO = userService.updateUser(id, userRequestDto);
        ResponseEntity<UserResponseDTO> userResponseDTOResponseEntity = ResponseEntity.
                status(HttpStatus.OK).body(userResponseDTO);
        return userResponseDTOResponseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ResponseEntity<String> userResponseDTOResponseEntity = ResponseEntity.
                status(HttpStatus.OK).body("User deleted successfully");
        return userResponseDTOResponseEntity;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> allUsers = userService.getAllUsers();
        ResponseEntity<List<UserResponseDTO>> body = ResponseEntity.status(HttpStatus.OK).
                body(allUsers);
        return body;
    }
}
