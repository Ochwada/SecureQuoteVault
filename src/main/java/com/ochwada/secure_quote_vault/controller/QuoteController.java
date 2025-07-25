package com.ochwada.secure_quote_vault.controller;


import com.ochwada.secure_quote_vault.model.Quote;
import com.ochwada.secure_quote_vault.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.controller
 * File: QuoteController.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 4:41 PM
 * Description: Rest Controller to expose/ for handling quote-related endpoints
 * - This controller provides endpoints to fetch a random quote from an external source and to retrieve all stored quotes.
 * *******************************************************
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/quotes")
public class QuoteController {
    /**
     * Service layer for handling quote-related business logic.
     */
    private final QuoteService service;

    /**
     * Fetches a random quote from the external API, attaches the current user's name,
     * saves it to the database, and returns it as the response.
     *
     * @param authentication the Spring Security authentication object
     * @return the newly saved {@link Quote}
     * @throws IOException if the external API response cannot be parsed
     */
    @GetMapping("/random")
    public ResponseEntity<Quote> getQuote(Authentication authentication) throws IOException {
        String username = authentication.getName();
        Quote quote = service.fetchAndSaveQuote(username);
        return ResponseEntity.ok(quote);
    }

    /**
     * Retrieves all quotes from the database that were fetched by the currently authenticated user.
     *
     * @param authentication the Spring Security {@link Authentication} object containing user details
     * @return a {@link ResponseEntity} containing a list of {@link Quote} objects fetched by the current user
     * @throws IOException if an error occurs during processing (though not expected in this method)
     */
    @GetMapping
    public ResponseEntity<List<Quote>> getAllQuotes(Authentication authentication) throws IOException {
        String username = authentication.getName();
        List<Quote> quotes = service.getQuotesByUser(username);
        return ResponseEntity.ok(quotes);
    }

}
