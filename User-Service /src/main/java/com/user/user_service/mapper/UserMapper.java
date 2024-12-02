package com.user.user_service.mapper;

import com.user.user_service.dtos.RegisterUserDto;
import com.user.user_service.dtos.AddressDto;
import com.user.user_service.entity.User;
import com.user.user_service.entity.Address;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection of ModelMapper and PasswordEncoder
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();  // You can also inject this if you want flexibility
    }

    // Convert User entity to RegisterUserDto
    public RegisterUserDto toRegisterUserDto(User user) {
        RegisterUserDto dto = modelMapper.map(user, RegisterUserDto.class);
        List<AddressDto> addressDtos = user.getAddresses().stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .collect(Collectors.toList());
        dto.setAddresses(addressDtos);

        dto.setPassword(null);  // Set the password to null or omit it

        return dto;
    }

    // Convert RegisterUserDto to User entity (with password encryption)
    public User toUserEntity(RegisterUserDto dto) {
        User user = modelMapper.map(dto, User.class);

        // Encrypt password before saving the user entity
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encryptedPassword);

        List<Address> addresses = dto.getAddresses().stream()
                .map(addressDto -> modelMapper.map(addressDto, Address.class))
                .collect(Collectors.toList());
        user.setAddresses(addresses);

        return user;
    }
}
