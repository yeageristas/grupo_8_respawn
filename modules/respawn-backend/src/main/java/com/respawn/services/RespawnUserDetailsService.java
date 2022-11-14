package com.respawn.services;

import com.respawn.entities.Pais;
import com.respawn.entities.Rol;
import com.respawn.entities.Usuario;
import com.respawn.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

public class RespawnUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.getByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not present"));
        User user = new User(usuario.getEmail(), usuario.getPassword(), Collections.singleton(usuario.getRol().getAuthority()));
        return user;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Rol> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(var role : roles) {
            authorities.add(role.getAuthority());
        }
        return authorities;
    }

    public void createUser(UserDetails user) throws ParseException {
        Usuario newUsuario = Usuario.builder()
                .email(user.getUsername())
                .password(user.getPassword())
                .fechaNacimiento(DateFormat.getDateInstance().parse("1970-01-01"))
                .pais(new Pais("ARGENTINA"))
                .rol(Rol.CLIENTE)
                .build();

        usuarioRepository.save(newUsuario);
    }
}
