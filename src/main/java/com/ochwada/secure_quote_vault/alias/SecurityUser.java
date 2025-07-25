package com.ochwada.secure_quote_vault.alias;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * *******************************************************
 * Package: com.ochwada.secure_quote_vault.alias
 * File: SecurityUser.java
 * Author: Ochwada
 * Date: Friday, 25.Jul.2025, 3:43 PM
 * Description:
 * Objective:
 * *******************************************************
 */


public class SecurityUser extends User {
    public SecurityUser(String username,
                        String password,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SecurityUser(String username,
                        String password,
                        boolean enabled,
                        boolean accountNonExpired,
                        boolean credentialsNonExpired,
                        boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled,
                accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
    }
}
