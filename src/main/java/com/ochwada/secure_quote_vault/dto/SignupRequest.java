package com.ochwada.secure_quote_vault.dto;


import jakarta.validation.constraints.*;
import lombok.*;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.dto
 * File: SignupRequest.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 5:09 PM
 * Description: To receive user registration data from client during auth/signup
 * - Data Transfer Object (DTO) representing the signup request payload.
 * Objective:
 * *******************************************************
 */

@Getter
@Setter
public class SignupRequest {
    /**
     * The username chosen by the user.
     * *
     * Must not be blank and must contain between 3 and 20 characters.
     */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username should be between 3 to 20 characters")
    private String username;

    /**
     * The password chosen by the user.
     * *
     * Must not be blank. It must:
     * - Be at least 10 characters long.
     * - Contain at least one uppercase letter
     * - Include at least one special character: _, -, *, ?, :
     */
    @NotBlank(message = "Password is required")
    @Size(min = 10, message = "Password should contain more than 10 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[_\\-\\*\\?:]).{10,}$",
            message = "Password must contain at least one uppercase letter and one special character (_, -, *, ?, :)"
    )
    private String password;
}
