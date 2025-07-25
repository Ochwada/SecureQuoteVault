package com.ochwada.secure_quote_vault.mapper;


import com.ochwada.secure_quote_vault.dto.SignupRequest;
import com.ochwada.secure_quote_vault.model.Role;
import com.ochwada.secure_quote_vault.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.mapper
 * File: UserMapper.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 5:12 PM
 * Description: Utility class for converting request DTOs to domain model objects.
 * -  Primarily used to map incoming signup data to a {@link User} entity before persisting it to the database.
 * *******************************************************
 */


public class UserMapper {
    /**
     * Converts a {@link SignupRequest} DTO into a {@link User} entity.
     * <p>
     * The password is securely encoded using the provided {@link PasswordEncoder},
     * and the user is assigned a default role of {@link Role#USER}.
     *
     * @param request the signup request containing raw user input
     * @param encoder the password encoder used to hash the user's password
     * @return a {@link User} entity ready for persistence
     */

    public static User toUser(SignupRequest request, PasswordEncoder encoder) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRoles(List.of(Role.USER));
        return user;
    }

}
