package com.respawn.services;

import com.respawn.entities.Usuario;
import com.respawn.repositories.GenericRepository;
import com.respawn.repositories.UsuarioRepository;
import com.respawn.transactional_services.UsuarioTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService extends GenericService<Usuario> implements UsuarioTransactionalService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService(GenericRepository<Usuario> baseRepository) {
        super(baseRepository);
    }

    @Override
    public Optional<Usuario> findByEmail() {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.getByEmail(email);
    }
}