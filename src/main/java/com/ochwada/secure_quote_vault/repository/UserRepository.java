package com.ochwada.secure_quote_vault.repository;


import com.ochwada.secure_quote_vault.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.repository
 * File: UserRepository.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 11:02 AM
 * Description: UserRepository provides CRUD operations for the {@link User} entity by extending {@link MongoRepository}.
 * - It includes a custom method to retrieve a user by username.
 * - Spring Data MongoDB automatically implements the interface at runtime, providing built-in CRUD methods
 * *******************************************************
 */


public interface UserRepository extends MongoRepository<User, String> {

 /** -----------------------------------------------------------
  * Inherits all basic CRUD methods from MongoRepository, like:
  * save(User)
  * findById(String id)
  * findAll()
  * deleteById(String id)
  ----------------------------------------------------------- */

 // ==================== Custom methods ==========================

 /**
  * Retrieves a user by their username.
  *
  * @param username the username of the user to be retrieved
  * @return an {@link Optional} containing the user if found, or empty if not found
  */
 Optional<User> findByUsername(String username);

 /**
  * Checks if a user with the given username exists in the database.
  *
  * @param username the username to check for existence
  * @return {@code true} if a user with the specified username exists, {@code false} otherwise
  */
 boolean existsByUsername(String username);
}
