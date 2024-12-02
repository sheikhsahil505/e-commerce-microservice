package com.user.user_service.service;


import com.mongodb.DuplicateKeyException;
import com.user.user_service.dtos.LoginUserDto;
import com.user.user_service.dtos.RegisterUserDto;
import com.user.user_service.entity.User;
import com.user.user_service.exception.EmailAlreadyExistsException;
import com.user.user_service.exception.GlobalExceptionHandler;
import com.user.user_service.mapper.UserMapper;
import com.user.user_service.repository.UserRepository;
import org.bson.BsonDocument;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder, UserMapper userMapper
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public RegisterUserDto signup(RegisterUserDto input) {
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new EmailAlreadyExistsException("email is already in use");
        }
        return userMapper.toRegisterUserDto(userRepository.save(userMapper.toUserEntity(input)));
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getUsername()).orElseThrow();
    }

}