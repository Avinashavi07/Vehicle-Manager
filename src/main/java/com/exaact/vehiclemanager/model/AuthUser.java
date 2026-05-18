package com.exaact.vehiclemanager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "registeredUsers")
public class AuthUser {

    @Id
    private String id;

    private String userName;

    private String password;

    @Indexed(unique = true)
    private String email;

    private String phoneNumber;

    private String resetToken;

    private LocalDateTime resetTokenExpiry;
}