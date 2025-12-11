package com.example.monolithicbackend.Mappers;

import com.example.monolithicbackend.DTO.UsersRelatedDTOs.AddressDTO;
import com.example.monolithicbackend.Entities.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMappers {

    public Address MpaAddressDtoTOAddress(AddressDTO addressDTO)
    {
        Address address=new Address();
        address.setAddressLine1(addressDTO.getAddressLine1());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setPinCode(addressDTO.getPinCode());
        address.setAddressType(addressDTO.getAddressType());
        return address;
    }

    public AddressDTO MpaAddressTOAddressDto(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressLine1(address.getAddressLine1());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setPinCode(address.getPinCode());
        addressDTO.setAddressType(address.getAddressType());
        return addressDTO;
    }
}
