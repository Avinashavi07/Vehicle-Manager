package com.exaact.vehiclemanager.service;

import com.exaact.vehiclemanager.dto.*;
import com.exaact.vehiclemanager.exception.DuplicateResourceException;
import com.exaact.vehiclemanager.exception.ResourceNotFoundException;
import com.exaact.vehiclemanager.exception.UnauthorizedException;
import com.exaact.vehiclemanager.model.AuthUser;
import com.exaact.vehiclemanager.repository.AuthUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest registerRequest) {
        if (authUserRepository
                .findByEmail(registerRequest.getEmail())
                .isPresent()) {

            throw new DuplicateResourceException(
                    "Email already registered"
            );
        }
        AuthUser authUser = com.exaact.vehiclemanager.model.AuthUser
                .builder()
                .userName(registerRequest.getUserName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .build();

        authUserRepository.save(authUser);
        return "User registered successfully!";
    }

    public LoginResponse login(LoginRequest loginRequest) {

        AuthUser authUser = authUserRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                loginRequest.getPassword(),
                authUser.getPassword()
        )) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtService.generateToken(authUser.getEmail());

        return LoginResponse.builder()
                .token(token)
                .userName(authUser.getUserName())
                .email(authUser.getEmail())
                .build();
    }

    public String forgotPassword(
            ForgotPasswordRequest request
    ) {

        AuthUser authUser =
                authUserRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Email not registered"
                                )
                        );

        String token = UUID.randomUUID().toString();

        authUser.setResetToken(token);

        authUser.setResetTokenExpiry(
                LocalDateTime.now().plusMinutes(15)
        );

        authUserRepository.save(authUser);

        /**
         * Later:
         * Send email here
         */

        return token;
    }

    public String resetPassword(
            ResetPasswordRequest request
    ) {

        AuthUser authUser =
                authUserRepository
                        .findByResetToken(
                                request.getToken()
                        )
                        .orElseThrow(() ->
                                new UnauthorizedException(
                                        "Invalid token"
                                )
                        );

        if (authUser.getResetTokenExpiry()
                .isBefore(LocalDateTime.now())) {

            throw new UnauthorizedException(
                    "Token expired"
            );
        }

        authUser.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        authUser.setResetToken(null);

        authUser.setResetTokenExpiry(null);

        authUserRepository.save(authUser);

        return "Password reset successful";
    }


}
