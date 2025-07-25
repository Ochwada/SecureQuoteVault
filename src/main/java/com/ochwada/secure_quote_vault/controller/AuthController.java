package com.ochwada.secure_quote_vault.controller;


import com.ochwada.secure_quote_vault.dto.JWTResponse;
import com.ochwada.secure_quote_vault.dto.SigninRequest;
import com.ochwada.secure_quote_vault.dto.SignupRequest;
import com.ochwada.secure_quote_vault.mapper.UserMapper;
import com.ochwada.secure_quote_vault.model.User;
import com.ochwada.secure_quote_vault.repository.UserRepository;
import com.ochwada.secure_quote_vault.security.JWTUtil;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.controller
 * File: AuthController.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 4:55 PM
 * Description:{@code AuthController} a Rest authentication Controller to expose authentication-related endpoints.
 * i.e. login and signup
 * - Handles user registration and login endpoints under the {@code /auth} path.
 * - Provides JWT-based authentication and stores new users securely.
 * *******************************************************
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    // Repository for interacting with the user collection/table
    private final UserRepository userRepository;

    // Password encoder for securely hashing user passwords
    private final PasswordEncoder passwordEncoder;

    // Spring Security's authentication manager
    private final AuthenticationManager authManager;

    // Utility class for generating and validating JWT tokens
    private final JWTUtil jwtUtil;


    /**
     * Registers a new user with the system.
     * *
     * Validates the incoming signup request, checks if the username is already taken,
     * hashes the password, assigns a default role, and persists the user.
     *
     * @param request the {@link SignupRequest} containing user registration data
     * @return a {@link ResponseEntity} indicating success or failure
     */
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequest request) {

        // Check if the username already exists in the database
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        // Map request to User entity and encode the password
        User user = UserMapper.toUser(request, passwordEncoder);

        // Save the new user to the database
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    /**
     * Authenticates a user and issues a JWT token upon successful login.
     * *
     * Validates the user's credentials using the {@link AuthenticationManager},
     * then generates a signed JWT for stateless authentication.
     *
     * @param request the {@link SigninRequest} containing login credentials
     * @return a {@link ResponseEntity} containing the JWT token if successful
     */
    @PostMapping("/signin")
    public ResponseEntity<JWTResponse> login(@Valid @RequestBody SigninRequest request) {
        // Authenticate the user using Spring Security
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );

        // Generate a JWT token after successful authentication
        String token = jwtUtil.generateToken(request.getUsername());

        // Return the token in a response DTO
        return ResponseEntity.ok(new JWTResponse(token));
    }

}
