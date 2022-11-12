package com.respawn.entities;


import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum Rol {
    CLIENTE("CLIENTE", new SimpleGrantedAuthority("CLIENTE")),
    VENDEDOR("VENDEDOR", new SimpleGrantedAuthority("VENDEDOR")),
    ADMINISTRADOR("ADMINISTRADOR", new SimpleGrantedAuthority("ADMINISTRADOR"));

    private final String nombre;
    private final SimpleGrantedAuthority authority;

    Rol(String nombre, SimpleGrantedAuthority authority) {
        this.nombre = nombre;
        this.authority = authority;
    }
}
