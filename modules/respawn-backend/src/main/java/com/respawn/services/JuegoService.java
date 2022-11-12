package com.respawn.services;

import com.respawn.entities.Juego;
import com.respawn.repositories.GenericRepository;
import com.respawn.repositories.JuegoRepository;
import com.respawn.transactional_services.JuegoTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class JuegoService extends GenericService<Juego> implements JuegoTransactionalService {
    @Autowired
    private JuegoRepository juegoRepository;
    public JuegoService(GenericRepository<Juego> baseRepository){
        super(baseRepository);
    }

    //-----------------------------------------------------
    @Transactional
    public boolean deleteById(long id) throws Exception {
        try {
            Optional<Juego> opt = this.juegoRepository.findById(id);
            if (!opt.isEmpty()) {
                Juego videojuego = opt.get();
                videojuego.setActivo(!videojuego.isActivo());
                this.juegoRepository.save(videojuego);
            } else {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Transactional
    public List<Juego> findAllByActivo() throws Exception{
        try {
            List<Juego> entities = this.juegoRepository.findAllByActivo();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Juego findByIdAndActivo(long id) throws Exception {
        try {
            Optional<Juego> opt = this.juegoRepository.findByIdAndActivo(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    //METODO PARA LA BUSQUEDA
    @Transactional
    public List<Juego> findByTitle(String q) throws Exception{
        try{
            List<Juego> entities = this.juegoRepository.findByTitle(q);
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
