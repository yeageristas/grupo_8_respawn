package com.respawn.services;

import com.respawn.repositories.GenericRepository;
import com.respawn.entities.GenericModel;
import com.respawn.transactional_services.GenericTransactionalService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class GenericService<E extends GenericModel> implements GenericTransactionalService<E, Long> {

    private final GenericRepository<E> baseRepository;

    public GenericService(GenericRepository<E> baseRepository) {
        this.baseRepository = baseRepository;
    }


    @Transactional
    public List<E> findAll() throws Exception {
        return baseRepository.findAll();
    }

    @Transactional
    public E findById(Long id) throws Exception {
        var opt = this.baseRepository.findById(id);
        return opt.get();
    }

    @Transactional
    public void save(E entity) throws Exception {
        this.baseRepository.save(entity);
    }

    @Transactional
    public void update(Long id, E entity) throws Exception {
        entity.setId(id);
        this.baseRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) throws Exception {
        var baseOpt = this.baseRepository.findById(id);
        if (baseOpt.isPresent()) {
            this.baseRepository.delete(baseOpt.get());
        } else {
            throw new Exception(String.format("%s with id %d not found in repository", baseOpt.getClass(), (Long) id));
        }
    }
}