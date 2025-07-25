package com.ochwada.secure_quote_vault.service;


import com.ochwada.secure_quote_vault.alias.SecurityUser;
import com.ochwada.secure_quote_vault.model.User;
import com.ochwada.secure_quote_vault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.service
 * File: UserDetailsServiceImpl.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 3:16 PM
 * Description:  Service class that implements Spring Security's {@link UserDetailsService} to provide user authentication
 * functionality based on application-specific user data.
 * - This implementation fetches user data from a {@link UserRepository} and maps the user's roles to
 * {@link SimpleGrantedAuthority} objects, which are used by Spring Security for authorization.
 * *******************************************************
 */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    /** Repository for accessing user data. */
    private final UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // Fetch user from DB
        User user = repository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Map roles to authorities
        List<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(
                        "ROLE_" + role.name()
                ))
                .toList();

        // Create custom SecurityUser
        UserDetails userDetails = new SecurityUser(
                user.getUsername(),
                user.getPassword(),
                authorities
        );

        return userDetails;
    }
}
