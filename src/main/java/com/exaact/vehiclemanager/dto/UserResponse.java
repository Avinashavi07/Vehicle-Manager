package com.exaact.vehiclemanager.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String id;

    private String name;

    private String email;

    private String mobile;

    private LocalDate dob;

    private LocalDateTime createdDate;
}