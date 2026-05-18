package com.exaact.vehiclemanager.controller;

import com.exaact.vehiclemanager.dto.*;
import com.exaact.vehiclemanager.service.ManagedUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ManagedUserController {

    private final ManagedUserService managedUserService;

    public ManagedUserController(
            ManagedUserService managedUserService
    ) {
        this.managedUserService = managedUserService;
    }

    /**
     * Create user
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>>
    createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {

        UserResponse response =
                managedUserService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                response,
                                "User created successfully"
                        )
                );
    }

    /**
     * Get all users
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>>
    getMyUsers() {

        List<UserResponse> response =
                managedUserService.getMyUsers();

        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }

    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>>
    getUserById(
            @PathVariable String id
    ) {

        UserResponse response =
                managedUserService.getUserById(id);

        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }

    /**
     * Update user
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>>
    updateUser(
            @PathVariable String id,
            @Valid @RequestBody UpdateUserRequest request
    ) {

        UserResponse response =
                managedUserService.updateUser(
                        id,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        response,
                        "User updated successfully"
                )
        );
    }

    /**
     * Delete user
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>>
    deleteUser(
            @PathVariable String id
    ) {

        managedUserService.deleteUser(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User deleted successfully"
                )
        );
    }
}