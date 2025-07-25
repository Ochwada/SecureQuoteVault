package com.ochwada.secure_quote_vault.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.model
 * File: Quote.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 10:48 AM
 * Description: Represents a quote document stored in the MongoDB collection named "quotes".
 * Objective:
 * *******************************************************
 */

@Document(collection = "quotes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quote {
    /**
     * Unique identifier for the quote document (MongoDB ObjectId).
     */
    @Id
    private String id;

    /**
     * The actual quote text.
     */
    private String quoteString;

    /**
     * The name of the person who originally said or wrote the quote.
     */
    private String author;

    /**
     * The username or ID of the user who fetched  the quote.
     */
    private String fetchedBy;

    /**
     * Timestamp indicating when the quote was stored.
     */
    private LocalDateTime createdAt;
}
