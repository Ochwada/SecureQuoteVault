package com.ochwada.secure_quote_vault.dto;


import jakarta.validation.constraints.*;
import lombok.*;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.dto
 * File: SigninRequest.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 5:08 PM
 * Description: DTO to get login credential from clients.
 * Objective:
 * *******************************************************
 */

@Getter
@Setter
public class SigninRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
