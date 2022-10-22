package com.respawn.services;

import com.respawn.entities.Categoria;
import com.respawn.repositories.CategoriaRepository;
import com.respawn.repositories.GenericRepository;
import com.respawn.transactional_services.CategoriaTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService extends GenericService<Categoria> implements CategoriaTransactionalService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaService(GenericRepository<Categoria> baseRepository) {
        super(baseRepository);
    }
}
