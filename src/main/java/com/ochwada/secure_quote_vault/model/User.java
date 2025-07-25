package com.ochwada.secure_quote_vault.model;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.model
 * File: User.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 10:07 AM
 * Description: Represent a registered user in the Secure Quote Vault system.  Stored as a {@link Document} users in the MongoDB
 * *******************************************************
 */

/**
 * - {@code @Document(collection = "users")}: Maps this class to the "users" collection in MongoDB.
 * - {@code @Data}: from Lombok: generates getters, setters, toString, equals, and hashCode.
 * - {@code @AllArgsConstructor}: from Lombok:generates a no-args constructor.
 * - {@code @NoArgsConstructor}: from Lombok:generates a constructor with all fields.
 */
@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    /**
     * A unique identifier for the user (MongoDB document ID).
     */
    @Id

    private String id;
    /**
     * The user's unique username.
     * *
     * This field is required and must not be blank.
     * {@code @Indexed(unique = true)}: Ensures the {@code name} field is unique across all user documents.
     * {@code @NotBlank}: Validates that the {@code name} is not null or empty during input.
     */
    @Indexed(unique = true)
    @NotBlank(message = "Username is required")
    private String username;

    /**
     * The user's password.
     * {@code @NotBlank}: Validates that the {@code name} is not null or empty during input.
     * This field is required and should be stored in a hashed format.
     */
    @NotBlank(message = "password is required")
    private String password;

    /**
     * The list of roles assigned to the user.
     * Used by Spring Security for access control -  injected from enum
     */
    private List<Role> roles;
}
