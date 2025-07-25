package com.ochwada.secure_quote_vault.security;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.security
 * File: JWTFilter.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 3:07 PM
 * Description:  It intercepts each request to check for JWT token in Authorization header.
 * - Filter that intercepts incoming HTTP requests to extract and validate JWT tokens.
 * - This filter runs once per request and is used to authenticate users based on a valid JWT token included in the
 * request header
 * - This class should be registered in the Spring Security filter chain.
 * *******************************************************
 */
@Component
@RequiredArgsConstructor
public class JWTFilter {
    private final JWTUtil jwtUtil;

}
