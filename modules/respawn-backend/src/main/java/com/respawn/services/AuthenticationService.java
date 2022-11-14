package com.respawn.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    public boolean isLoggedIn() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return null != auth && !("anonymousUser").equals(auth.getName());
    }

    public List<String> getRolesForUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !"anonymousUser".equals(auth.getName())) {
            return auth.getAuthorities().stream().map(Object::toString).collect(Collectors.toUnmodifiableList());
        }
        return Collections.emptyList();
    }

    public String getUserEmail() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !"anonymousUser".equals(auth.getName())) {
            return auth.getName();
        }
        return null;
    }
}
