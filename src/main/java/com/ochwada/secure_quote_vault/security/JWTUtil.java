package com.ochwada.secure_quote_vault.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.security
 * File: JWTUtil.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 12:10 PM
 * Description: This utility class handles everything related to JWT:
 * - Creating (signing) JWT tokens
 * - Extracting information (claims) from JWT tokens
 * - Validating JWT tokens
 * It uses the JJWT (io.jsonwebtoken) library to work with tokens.
 * *******************************************************
 */

@Component // Marks this class as a Spring Bean so it can be injected where needed (e.g. in controllers)
public class JWTUtil {

    /**
     * Secret key used to sign the token.
     */
    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Converts the raw JWT Secret string into a secure HMAC-SHA256 {@link Key}
     * *
     * This key is used to digitally sign and verify JWTs using the HS256 algorithm.
     * The secret must be at least 256 bits (32 bytes) long to meet the key length requirements for HS256.
     *
     * @return a {@link Key} suitable for signing and verifying JWTs
     * @throws IllegalArgumentException if the key length is insufficient
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * ---------------------------------
     * 1. Token Generator
     * ---------------------------------
     * Generates a JWT token for a given username.
     * *
     * Generates a signed JWT (JSON Web Token) that contains the user's username as its subject.
     * The token is valid for 24 hours from the time of generation.
     * This token can be used for authenticating and authorizing users across secure endpoints.
     * The token is signed using HMAC SHA-256 algorithm with a secret key (`jwtSecret`).
     *
     * @param username the username to embed as the subject of the JWT
     * @return a compact, URL-safe, signed JWT string
     */
    public String generateToken(String username) {

        long expirationMillis = 24 * 60 * 60 * 1000;  // 1 day in milliseconds
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()  // Start building the JWT using JJWT's fluent builder API
                .setSubject(username) // standard claim 'sub' = username
                .setIssuedAt(now) // token creation time = now
                .setExpiration(expiryDate) // expires in 1 hour
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // creating the token
                .compact(); // build the token into a compact string
    }

    /**
     * ---------------------------------
     * 2. Claims Extraction and Validation
     * ---------------------------------
     * Extract the username from the given token
     *
     * @param token the JWT token
     * @return the subject (username)
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder() // entry point to parse a token
                .setSigningKey(getSigningKey()) // use the jwt secret to unlock the token
                .build() // finalizing the parser configuration
                .parseClaimsJws(token)  // Parses the token and validate its signature and expiration
                .getBody()// get the Payload
                .getSubject(); // get the 'sub' claim (username)

    }

    /**
     * Check if the token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parserBuilder() // entry point to parse a token parser
                .setSigningKey(getSigningKey()) // use the jwt secret to unlock the token
                .build()// finalizing the parser configuration
                .parseClaimsJws(token) // Parse and validate token
                .getBody() // get the Payload
                .getExpiration(); // Extracts the expiration date
        return expirationDate.before(new Date()); // Returns true if expired
    }

    /**
     * Validates the token by checking the username and ensuring it is not expired.
     *
     * @param token    the JWT token to validate
     * @param username the expected username
     * @return true if the token is valid and matches the username
     */
    public boolean isTokenValid(String token, String username) {
        String extracted = extractUsername(token);
        return extracted.equals(username) && !isTokenExpired(token);
    }


}
