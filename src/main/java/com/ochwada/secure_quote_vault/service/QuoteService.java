package com.ochwada.secure_quote_vault.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ochwada.secure_quote_vault.model.Quote;
import com.ochwada.secure_quote_vault.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.service
 * File: QuoteService.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 11:32 AM
 * Description: Service layer for Quotes operations.
 * - Service class for handling business logic related to quotes
 * - Fetching a random quote from an external quote API
 * - Saving the quote to MongoDB along with the user information
 * *******************************************************
 */

@Service
@RequiredArgsConstructor
public class QuoteService {
    /**
     * Used to make HTTP requests to external quote APIs.
     */
    private final RestTemplate restTemplate;

    /**
     * Repository interface for performing CRUD operations on quotes.
     */
    private final QuoteRepository repository;

    /**
     * The URL endpoint for retrieving random quotes from an external service.
     * Injected from application properties.
     */
    @Value("${random.quote.url}")
    private String randomQuoteUrl;

    /**
     * Jackson ObjectMapper used for parsing JSON responses.
     */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Fetches a random quote from an external API and prepares it for persistence by attaching the current user's
     * username and parsing all required fields.
     * *
     * The method performs the following steps:
     * 1. Makes an HTTP GET request to a remote API defined by {@code randomQuoteUrl}.
     * 2. Parses the JSON response into a {@code JsonNode}.
     * 3. Maps relevant fields (quote string, author, creation date) to a {@link Quote} object.
     * 4. Attaches the requesting username to the quote.
     * 5. Inserts the new {@code Quote} into the MongoDB collection.
     *
     * @param username the username of the person fetching the quote
     * @return the persisted {@link Quote} object
     * @throws IOException if the API response cannot be parsed into JSON
     */
    public Quote fetchAndSaveQuote(String username) throws IOException {
        //Call external API
        String response = restTemplate.getForObject(randomQuoteUrl, String.class);

        // Parses the JSON string response manually into a JsonNode tree structure
        JsonNode jsonNode = mapper.readTree(response);

        // Map to a Quote Object
        Quote quote = new Quote();
        quote.setQuoteString(jsonNode.get("quoteString").asText());
        quote.setAuthor(jsonNode.get("author").asText());

        // Parse and map createdAt date using a custom formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        quote.setCreatedAt(LocalDateTime.parse(jsonNode.get("createdAt").asText(), formatter));

        // Set the username of the person who fetched the quote
        quote.setFetchedBy(username);

        // Save to database and return the persisted quote
        return repository.insert(quote);
    }

    /**
     * Retrieves all quotes from the database that were fetched by a specific user.
     * <p>
     * This method queries the {@link QuoteRepository} for all {@link Quote} entries where the {@code fetchedBy}
     * field matches the given username.
     *
     * @param username the username of the user whose quotes should be retrieved
     * @return a list of {@link Quote} objects associated with the given user
     */
    public List<Quote> getQuotesByUser(String username) {
        return repository.findByFetchedBy(username);
    }

}
