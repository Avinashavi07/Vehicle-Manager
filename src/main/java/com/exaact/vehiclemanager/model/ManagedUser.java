package com.exaact.vehiclemanager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "managedUsers")
public class ManagedUser {

    @Id
    private String id;

    private String name;

    private String email;

    private String mobile;

    private LocalDate dob;

    private LocalDateTime createdDate;

    /**
     * Logged-in user who created this user
     */
    private String createdBy;
}