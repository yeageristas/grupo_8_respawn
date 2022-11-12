package com.respawn.transactional_services;

import com.respawn.entities.Usuario;

import java.util.Optional;

public interface UsuarioTransactionalService extends GenericTransactionalService<Usuario, Long> {
    Optional<Usuario> findByEmail();

    Optional<Usuario> findByEmail(String email);
}
