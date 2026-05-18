package com.exaact.vehiclemanager.repository;

import com.exaact.vehiclemanager.model.ManagedUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ManagedUserRepository
        extends MongoRepository<ManagedUser, String> {

    List<ManagedUser> findByCreatedBy(String createdBy);

    Optional<ManagedUser> findByIdAndCreatedBy(
            String id,
            String createdBy
    );
    boolean existsByEmailAndCreatedBy(
            String email,
            String createdBy
    );
    boolean existsByEmailAndCreatedByAndIdNot(
            String email,
            String createdBy,
            String id
    );
}