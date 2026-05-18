package com.exaact.vehiclemanager.service;

import com.exaact.vehiclemanager.model.AuthUser;
import com.exaact.vehiclemanager.repository.AuthUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    public CustomUserDetailsService(
            AuthUserRepository authUserRepository
    ) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String email
    ) throws UsernameNotFoundException {

        AuthUser authUser = authUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        return new org.springframework.security.core.userdetails.User(
                authUser.getEmail(),
                authUser.getPassword(),
                Collections.singleton(
                        new SimpleGrantedAuthority("ROLE_USER")
                )
        );
    }
}