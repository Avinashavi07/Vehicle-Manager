package com.exaact.vehiclemanager.controller;

import com.exaact.vehiclemanager.dto.*;
import com.exaact.vehiclemanager.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        String response = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        response
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {

        LoginResponse response = authService.login(loginRequest);

        return ResponseEntity.ok(
                ApiResponse.success(
                        response,
                        "Login successful"
                )
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>>
    forgotPassword(
            @Valid @RequestBody
            ForgotPasswordRequest request
    ) {

        String token =
                authService.forgotPassword(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        token,
                        "Password reset token generated"
                )
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>>
    resetPassword(
            @Valid @RequestBody
            ResetPasswordRequest request
    ) {

        String response =
                authService.resetPassword(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        response
                )
        );
    }

}
