package com.respawn.services;

import com.respawn.entities.EstadoJuego;
import com.respawn.repositories.EstadoJuegoRepository;
import com.respawn.repositories.GenericRepository;
import com.respawn.transactional_services.EstadoJuegoTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoJuegoService extends GenericService<EstadoJuego> implements EstadoJuegoTransactionalService {

    @Autowired
    private EstadoJuegoRepository estadoJuegoRepository;

    public EstadoJuegoService(GenericRepository<EstadoJuego> baseRepository) {
        super(baseRepository);
    }
}