package com.example.vkr.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUser extends User implements CustomUserDetails {

    private final String email;

    public SecurityUser(String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = email;
    }

    public SecurityUser(String username, String email, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.email = email;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
