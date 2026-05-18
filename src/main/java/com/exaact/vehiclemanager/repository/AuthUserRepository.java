package com.exaact.vehiclemanager.repository;

import com.exaact.vehiclemanager.model.AuthUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends MongoRepository<AuthUser, String> {
    Optional<AuthUser> findByEmail(String email);
    Optional<AuthUser> findByResetToken(String resetToken);
}
