package com.respawn.services;

import com.respawn.entities.Favoritos;
import com.respawn.repositories.FavoritosRepository;
import com.respawn.repositories.GenericRepository;
import com.respawn.transactional_services.FavoritosTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritosService extends GenericService<Favoritos> implements FavoritosTransactionalService {
    @Autowired
    private FavoritosRepository favoritosRepository;

    public FavoritosService(GenericRepository<Favoritos> baseRepository){
        super(baseRepository);
    }
}
