package com.exaact.vehiclemanager.service;

import com.exaact.vehiclemanager.dto.CreateUserRequest;
import com.exaact.vehiclemanager.dto.UpdateUserRequest;
import com.exaact.vehiclemanager.dto.UserResponse;
import com.exaact.vehiclemanager.exception.DuplicateResourceException;
import com.exaact.vehiclemanager.exception.ResourceNotFoundException;
import com.exaact.vehiclemanager.model.AuthUser;
import com.exaact.vehiclemanager.model.ManagedUser;
import com.exaact.vehiclemanager.repository.AuthUserRepository;
import com.exaact.vehiclemanager.repository.ManagedUserRepository;
import com.exaact.vehiclemanager.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManagedUserService {

    private final ManagedUserRepository managedUserRepository;

    private final AuthUserRepository authUserRepository;

    public ManagedUserService(
            ManagedUserRepository managedUserRepository,
            AuthUserRepository authUserRepository
    ) {
        this.managedUserRepository = managedUserRepository;
        this.authUserRepository = authUserRepository;
    }

    /**
     * Create user under logged-in owner
     */
    public UserResponse createUser(
            CreateUserRequest request
    ) {

        String email =
                SecurityUtil.getLoggedInUserEmail();

        AuthUser authUser =
                authUserRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Logged-in user not found"
                                )
                        );

        boolean exists =
                managedUserRepository
                        .existsByEmailAndCreatedBy(
                                request.getEmail(),
                                authUser.getId()
                        );

        if (exists) {
            throw new DuplicateResourceException(
                    "User email already exists"
            );
        }

        ManagedUser managedUser =
                ManagedUser.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .mobile(request.getMobile())
                        .dob(request.getDob())
                        .createdDate(LocalDateTime.now())
                        .createdBy(authUser.getId())
                        .build();

        managedUserRepository.save(managedUser);

        return mapToResponse(managedUser);
    }

    /**
     * Get all users of logged-in owner
     */
    public List<UserResponse> getMyUsers() {

        String email =
                SecurityUtil.getLoggedInUserEmail();

        AuthUser authUser =
                authUserRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Logged-in user not found"
                                )
                        );

        List<ManagedUser> users =
                managedUserRepository.findByCreatedBy(
                        authUser.getId()
                );

        return users.stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Get single user by ID
     */
    public UserResponse getUserById(String id) {

        String email =
                SecurityUtil.getLoggedInUserEmail();

        AuthUser authUser =
                authUserRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Logged-in user not found"
                                )
                        );

        ManagedUser user =
                managedUserRepository
                        .findByIdAndCreatedBy(
                                id,
                                authUser.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        return mapToResponse(user);
    }

    /**
     * Update user
     */
    public UserResponse updateUser(
            String id,
            UpdateUserRequest request
    ) {

        String email =
                SecurityUtil.getLoggedInUserEmail();

        AuthUser authUser =
                authUserRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Logged-in user not found"
                                )
                        );

        ManagedUser user =
                managedUserRepository
                        .findByIdAndCreatedBy(
                                id,
                                authUser.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        boolean exists =
                managedUserRepository
                        .existsByEmailAndCreatedByAndIdNot(
                                request.getEmail(),
                                authUser.getId(),
                                id
                        );

        if (exists) {
            throw new DuplicateResourceException(
                    "User email already exists"
            );
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setDob(request.getDob());

        managedUserRepository.save(user);

        return mapToResponse(user);
    }

    /**
     * Delete user
     */
    public void deleteUser(String id) {

        String email =
                SecurityUtil.getLoggedInUserEmail();

        AuthUser authUser =
                authUserRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Logged-in user not found"
                                )
                        );

        ManagedUser user =
                managedUserRepository
                        .findByIdAndCreatedBy(
                                id,
                                authUser.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        managedUserRepository.delete(user);
    }

    /**
     * Entity → DTO mapper
     */
    private UserResponse mapToResponse(
            ManagedUser user
    ) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .dob(user.getDob())
                .createdDate(user.getCreatedDate())
                .build();
    }
}