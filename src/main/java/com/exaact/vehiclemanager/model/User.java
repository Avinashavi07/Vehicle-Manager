package com.exaact.vehiclemanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "registeredUsers")
public class User {
    @Id
    private String id;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;

    public User(String userName, String password, String email, String phoneNumber) {
    }
}
