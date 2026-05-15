package com.exaact.vehiclemanager.service;

import com.exaact.vehiclemanager.dto.ApiResponse;
import com.exaact.vehiclemanager.dto.RegisterRequest;
import com.exaact.vehiclemanager.model.User;
import com.exaact.vehiclemanager.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(RegisterRequest registerRequest) {
        User user = User
                .builder()
                .userName(registerRequest.getUserName())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .build();
        userRepository.save(user);
        return "User registered successfully!";
    }
}
