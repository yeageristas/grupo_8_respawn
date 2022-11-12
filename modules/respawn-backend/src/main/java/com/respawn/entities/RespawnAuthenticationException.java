package com.respawn.entities;

import org.springframework.security.core.AuthenticationException;

public class RespawnAuthenticationException extends AuthenticationException {
    public RespawnAuthenticationException(String msg) {
        super(msg);
    }
}
