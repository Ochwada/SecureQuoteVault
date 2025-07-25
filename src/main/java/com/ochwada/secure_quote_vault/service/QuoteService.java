package com.ochwada.secure_quote_vault.service;


import com.ochwada.secure_quote_vault.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
 private final RestTemplate restTemplate;
 private final QuoteRepository repository;

 
 private String randomQuoteUrl;


}
