package com.ochwada.secure_quote_vault.security;


import com.ochwada.secure_quote_vault.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
public class JWTFilter extends OncePerRequestFilter {

    /**
     * Dependency:{@link JWTUtil} – utility class for validating and parsing JWT tokens.
     */
    private final JWTUtil jwtUtil;

    /**
     * Dependency {@link UserDetailsServiceImpl} – service to load user details from the database.
     */
    private final UserDetailsServiceImpl serviceImpl;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Extract the Authorization header from the HTTP request
        // Expected format: "Authorization: Bearer <jwt_token>"
        String authHeader = request.getHeader("Authorization");

        // Check if the header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Remove the "Bearer " prefix to extract the actual JWT token
            String token = authHeader.substring(7);

            // Extract the username from the JWT token
            String username = jwtUtil.extractUsername(token);

            // Proceed only if username is found and no authentication exists in the current context
            if (username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load user details from the database using the extracted username
                UserDetails userDetails = serviceImpl.loadUserByUsername(username);

                // Validate the token against the loaded user details
                if (jwtUtil.isTokenValid(token, userDetails.getUsername())) {

                    // Create an authentication (object ) with user details and authorities
                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // Attach (inject) request-specific details (e.g., remote IP, session ID) to the authentication token
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    // Store the authentication token in the SecurityContext to complete login
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        // Continue the filter chain regardless of authentication outcome
        filterChain.doFilter(request, response);
    }
}
