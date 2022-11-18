package com.respawn.services;

import com.respawn.entities.Pais;
import com.respawn.repositories.GenericRepository;
import com.respawn.repositories.PaisRepository;
import com.respawn.transactional_services.GenericTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisService extends GenericService<Pais> implements GenericTransactionalService<Pais, Long> {

    @Autowired
    private PaisRepository paisRepository;

    public PaisService(GenericRepository<Pais> baseRepository) {
        super(baseRepository);
    }

    public Pais findByNombre(String name) {
        return paisRepository.findByNombre(name);
    }
}
