package com.ochwada.secure_quote_vault.repository;


import com.ochwada.secure_quote_vault.model.Quote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.repository
 * File: QuoteRepository.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 11:21 AM
 * Description: QuoteRepository  provides CRUD operations for the {@link Quote} entity by extending {@link MongoRepository}.
 * *******************************************************
 */


public interface QuoteRepository extends MongoRepository<Quote, String> {
    /** -----------------------------------------------------------
     * Inherits all basic CRUD methods from MongoRepository, like:
     * save(Quote)
     * findById(String id)
     * findAll()
     * deleteById(String id)
     ----------------------------------------------------------- */

    // ==================== Custom methods ==========================

    /**
     * Retrieves a list of quotes fetched by a specific user.
     *
     * @param fetchedBy the username or ID of the user who fetched  the quotes
     * @return a list of {@link Quote} objects created by the specified user;
     * returns an empty list if no quote are found
     */
    List<Quote> findByFetchedBy(String fetchedBy);
}
