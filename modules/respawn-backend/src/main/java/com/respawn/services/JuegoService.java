package com.respawn.services;

import com.respawn.entities.Juego;
import com.respawn.repositories.GenericRepository;
import com.respawn.repositories.JuegoRepository;
import com.respawn.transactional_services.JuegoTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class JuegoService extends GenericService<Juego> implements JuegoTransactionalService {
    @Autowired
    private JuegoRepository juegoRepository;
    public JuegoService(GenericRepository<Juego> baseRepository){
        super(baseRepository);
    }

    @Transactional
    public List<Juego> findByTitle(String q) throws Exception {
        try{
            List<Juego> entities = this.juegoRepository.findByTitle(q);
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
