package com.respawn.repositories;

import com.respawn.entities.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsuarioRepository extends GenericRepository<Usuario> {
    Optional<Usuario> getByEmail(String email);
}
