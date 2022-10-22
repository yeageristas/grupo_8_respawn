package com.respawn.services;

import com.respawn.entities.Idioma;
import com.respawn.repositories.IdiomaRepository;
import com.respawn.repositories.GenericRepository;
import com.respawn.transactional_services.IdiomaTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdiomaService extends GenericService<Idioma> implements IdiomaTransactionalService {

    @Autowired
    private IdiomaRepository idiomaRepository;

    public IdiomaService(GenericRepository<Idioma> baseRepository) {
        super(baseRepository);
    }
}