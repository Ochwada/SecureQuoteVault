package com.ochwada.secure_quote_vault.model;


/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.model
 * File: Role.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 10:01 AM
 * Description: Enum representing the roles assigned to users in the Secure Quote Vault application.
 * - This enum defines the different levels of access and permissions available within the system. It helps manage
 * authorization and restricts functionalities based on the assigned role.
 * *******************************************************
 */

/**
 *   {@code USER} – Basic access to quotes and user-level features.
 *   {@code ADMIN} – Management-level access including user and quote moderation.
 *   {@code MODERATOR} – Limited admin access, primarily for reviewing content.
 *   {@code SUPER_ADMIN} – Full system access including application settings and logs.
 */

public enum Role {
    USER,
    ADMIN,
    MODERATOR,
    SUPER_ADMIN
}
