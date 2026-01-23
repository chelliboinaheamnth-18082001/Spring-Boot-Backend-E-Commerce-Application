package com.example.user_service.User_Mappers;


import com.example.user_service.Entites.Address;
import com.example.user_service.Entites.Users;
import com.example.user_service.User_DTOs.AddressDTO;
import com.example.user_service.User_DTOs.UserRequestDto;
import com.example.user_service.User_DTOs.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class UserMappers {
    private final AddressMappers addressMappers;

    public Users MpaUsesDtoToUser(UserRequestDto userrequest)
    {

       Users users=new Users();
       users.setFirstName(userrequest.getFirstName());
       users.setLastName(userrequest.getLastName());
       users.setEmail(userrequest.getEmail());
       users.setMobileNumber(userrequest.getMobileNumber());
        Address address = addressMappers.MpaAddressDtoTOAddress(userrequest.getAddress());
        users.setAddress(address);
       users.setUserRole(userrequest.getUserRole());
       return users;
    }

    public UserResponseDTO MpaUserToUserResponseDTO(Users users) {
        
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setFirstName(users.getFirstName());
        userResponseDTO.setLastName(users.getLastName());
        userResponseDTO.setEmail(users.getEmail());
        userResponseDTO.setMobileNumber(users.getMobileNumber());
        userResponseDTO.setAddress(addressMappers.MpaAddressTOAddressDto(users.getAddress()));
        userResponseDTO.setUserRole(users.getUserRole());
        return userResponseDTO;
    }

    public Address MpaAddressDtoTOAddress(AddressDTO address) {
        Address address1 = new Address();
        address1.setAddressLine1(address.getAddressLine1());
        address1.setCity(address.getCity());
        address1.setState(address.getState());
        address1.setCountry(address.getCountry());
        address1.setPinCode(address.getPinCode());
        address1.setAddressType(address.getAddressType());
        return address1;
    }
}
