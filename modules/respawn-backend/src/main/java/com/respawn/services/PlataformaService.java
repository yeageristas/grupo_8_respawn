package com.respawn.services;

import com.respawn.entities.Plataforma;
import com.respawn.repositories.PlataformaRepository;
import com.respawn.repositories.GenericRepository;
import com.respawn.transactional_services.PlataformaTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlataformaService extends GenericService<Plataforma> implements PlataformaTransactionalService {

    @Autowired
    private PlataformaRepository plataformaRepository;

    public PlataformaService(GenericRepository<Plataforma> baseRepository) {
        super(baseRepository);
    }
}