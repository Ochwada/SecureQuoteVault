package com.ochwada.secure_quote_vault.config;


import com.ochwada.secure_quote_vault.security.JWTFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.config
 * File: SecurityConfig.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 4:24 PM
 * Description: Main/ Central Security Configuration.
 * - Configures HTTP security settings for the application.
 * Objective: Secure API endpoints using JWT authentication.
 * *******************************************************
 */

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * JWT filter used to validate and process tokens in incoming requests.
     * Automatically injected via constructor using Lombok's {@code @RequiredArgsConstructor}.
     */
    private final JWTFilter jwtFilter;


    /**
     * Configures the security filter chain for HTTP requests.
     * *
     * Disables CSRF protection for stateless JWT-based APIs, permits all requests to authentication endpoints, and
     * secures all other endpoints. The JWTFilter is registered before Spring's UsernamePasswordAuthenticationFilter.
     *
     * @param httpSecurity the {@link HttpSecurity} to customize security behavior
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs while building the filter chain
     */
    @Bean // Makes it part of the Spring context - provided when needed (an object)
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Disable CSRF since JWT is used and the API is stateless
                .csrf(AbstractHttpConfigurer::disable)

                // Configure endpoint authorization rules
                .authorizeHttpRequests(auth -> auth

                        // Allow public access to any endpoint under /auth (e.g., login, register)
                        .requestMatchers("/auth/**").permitAll()

                        // Require authentication for all other endpoints
                        .anyRequest().authenticated() // Secure all other endpoints (require jwt based authentication)
                )

                // Add custom JWT filter before Spring Security's default username-password filter
                .addFilterBefore(
                        jwtFilter, UsernamePasswordAuthenticationFilter.class // Add JWT filter
                );

        // Finalize and return the configured SecurityFilterChain
        return httpSecurity.build(); // Returns the fully configured SecurityFilterChain
    }

    /**
     * Defines the password encoder bean for hashing user passwords.
     * *
     * This bean uses {@link BCryptPasswordEncoder}, a strong hashing function based on BCrypt,
     * to encode passwords before storing them in the database. It is automatically registered in
     * the Spring context and injected wherever a {@link PasswordEncoder} is required.
     *
     * @return a {@link PasswordEncoder} implementation using BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes the {@link AuthenticationManager} bean for use in the application context.
     * <p>
     * The {@link AuthenticationManager} is responsible for processing authentication requests (e.g., verifying user
     * credentials during login). This method retrieves the pre-configured authentication manager from the provided
     * {@link AuthenticationConfiguration}.
     *
     * @param config the Spring Security {@link AuthenticationConfiguration}
     * @return the central {@link AuthenticationManager} used for authentication processing
     * @throws Exception if retrieval of the authentication manager fails
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

}
