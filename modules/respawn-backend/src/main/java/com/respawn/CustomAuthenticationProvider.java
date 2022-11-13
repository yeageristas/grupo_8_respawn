package com.respawn;

import com.respawn.entities.RespawnAuthenticationException;
import com.respawn.entities.Usuario;
import com.respawn.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

import java.util.Objects;
import java.util.Optional;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        Optional<Usuario> usuario = usuarioRepository.getByEmail(username);
        if(usuario.isEmpty()) {
            throw new RespawnAuthenticationException("Wrong Auth");
        }
        if(!Objects.equals(password, usuario.get().getPassword())) {
            throw new RespawnAuthenticationException("Wrong password!");
        }
        User user = Usuario.of(usuario.get());
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
