package com.example.eventplanner.config;

import com.example.eventplanner.model.auth.Role;
import com.example.eventplanner.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserFactory {

    public static SecurityUser create(User user) {
        Collection<? extends GrantedAuthority> authorities;

        try {
            Role role = user.getRole(); // koristi Enum
            String roleName = role.name(); // npr. "EO", "AU", itd.

            authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleName));
        } catch (Exception e) {
            authorities = List.of();
        }

        return new SecurityUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }


}
